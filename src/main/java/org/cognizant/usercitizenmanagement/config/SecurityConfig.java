package org.cognizant.usercitizenmanagement.config;

import org.cognizant.usercitizenmanagement.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
//                                .requestMatchers("/api/users/login", "/api/users/createUser","/api/citizens/createCitizen").permitAll() // ALLOW THESE WITHOUT LOGIN
//                                .requestMatchers("/api/citizens/createCitizen", "/api/citizens/update/{id}", "/api/documents/upload","/api/documents/delete/{id}", "/api/users/login").hasRole("CITIZEN")
//                                .requestMatchers("/api/logs/GetAllLogs", "/api/logs/CreateLog").hasRole("AUDITOR")
//                                .requestMatchers("/api/compliance-records/**").hasRole("COMPLIANCE")
//                                .requestMatchers("/api/citizens/getCitizenById/{id}", "/api/citizens/getAllCitizens", "/api/citizens/delete/{id}", "/api/documents/getDocById/{id}").hasRole("OFFICER")
//                                .requestMatchers("/api/users/getByUserId/{id}", "/api/users/getAllUsers","/api/users/update/{id}", "/api/users/delete/{id}").hasRole("MANAGER")
//                                .requestMatchers("/api/users/**").hasRole("ADMIN")
//                                .anyRequest().authenticated() // LOCK EVERYTHING ELSE
                                .anyRequest().permitAll()
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
