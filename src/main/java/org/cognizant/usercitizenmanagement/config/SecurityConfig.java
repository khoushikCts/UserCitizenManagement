package org.cognizant.usercitizenmanagement.config;

import org.cognizant.usercitizenmanagement.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Use this instead of @EnableWebMvc for Security
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC ACCESS
                        .requestMatchers("/api/users/login", "/api/users/createUser", "/api/citizens/createCitizen")
                        .permitAll()

                        // 2. LOG ACCESS (Auditor + Compliance + Manager + Officer)
                        .requestMatchers("/api/logs/**")
                        .hasAnyRole("AUDITOR", "COMPLIANCE", "MANAGER", "OFFICER")

                        // 3. CITIZEN SPECIFIC (Actions only a Citizen does for themselves)
                        .requestMatchers("/api/users/update/**", "/api/citizens/update/**",
                                "/api/documents/upload", "/api/documents/delete/**",
                                "/api/reports/createreport")
                        .hasRole("CITIZEN")

                        // 4. SHARED ACCESS: GET CITIZEN BY ID (Citizen + Staff roles)
                        // We move this here so that BOTH the Citizen and the Staff can access it.
                        .requestMatchers("/api/citizens/getCitizenById/**")
                        .hasAnyRole("CITIZEN", "AUDITOR", "COMPLIANCE", "OFFICER", "MANAGER")

                        // 5. STAFF DATA VIEWING (Excluding the individual Citizen)
                        .requestMatchers("/api/citizens/getAllCitizens",
                                "/api/documents/getDocById/**", "/api/reports/getallreports",
                                "/api/reports/getreportbyid/**", "/api/reports/*/details",
                                "/api/users/getUSerById/**")
                        .hasAnyRole("AUDITOR", "COMPLIANCE", "OFFICER", "MANAGER")

                        // 6. OFFICER & MANAGER SPECIFIC
                        .requestMatchers("/api/citizens/delete/**", "/api/citizens/updateStatus/**")
                        .hasAnyRole("OFFICER", "MANAGER")

                        // 7. MANAGER ONLY (Administrative)
                        .requestMatchers("/api/users/getAllUsers", "/api/users/delete/**", "/api/reports/delete/**")
                        .hasRole("MANAGER")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // In a microservices architecture, this service typically doesn't
            // load users. It just trusts the decoded JWT token.
            throw new UsernameNotFoundException("User lookup not supported in this microservice");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


}
