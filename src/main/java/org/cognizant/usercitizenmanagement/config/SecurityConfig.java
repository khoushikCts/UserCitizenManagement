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
                        // 1. PUBLIC ACCESS (No login required)
                        .requestMatchers("/api/users/login", "/api/users/createUser", "/api/citizens/createCitizen")
                        .permitAll()

                        // 2. CITIZEN ROLE
                        // Covers profile updates, document uploads/deletes, and report creation
                        .requestMatchers("/api/users/update/**", "/api/citizens/update/**",
                                "/api/documents/upload", "/api/documents/delete/**",
                                "/api/reports/createreport")
                        .hasRole("CITIZEN")

                        // 3. LOG ACCESS (Auditor + Compliance + Manager)
                        .requestMatchers("/api/logs/**")
                        .access(AuthorizationManagers.anyOf(
                                AuthorityAuthorizationManager.hasAnyRole("AUDITOR", "COMPLIANCE", "MANAGER","OFFICER")
                        ))

                        // 4. DATA VIEWING (The large union of Auditor, Compliance, Officer, Manager)
                        .requestMatchers("/api/citizens/getCitizenById/**", "/api/citizens/getAllCitizens",
                                "/api/documents/getDocById/**", "/api/reports/getallreports",
                                "/api/reports/getreportbyid/**", "/api/reports/*/details","/api/users/getUSerById/**")
                        .hasAnyRole("AUDITOR", "COMPLIANCE", "OFFICER", "MANAGER")

                        // 5. OFFICER & MANAGER SPECIFIC (Staff Union)
                        .requestMatchers("/api/citizens/delete/**")
                        .hasAnyRole("OFFICER", "MANAGER")

                        // 6. MANAGER ONLY (Administrative/Destructive actions)
                        .requestMatchers("/api/users/getAllUsers", "/api/users/delete/**", "/api/reports/delete/**")
                        .hasRole("MANAGER")

                        // 5. OFFICER SPECIFIC
                        .requestMatchers("/api/citizens/delete/**", "/api/citizens/updateStatus/**")
                        .hasRole("OFFICER")

                        .anyRequest().authenticated()
                ) // LOCK EVERYTHING ELSE
//                                .anyRequest().permitAll()
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
