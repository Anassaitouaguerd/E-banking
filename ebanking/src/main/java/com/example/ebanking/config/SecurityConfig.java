package com.example.ebanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF
                .cors(cors -> cors.disable())   // Disable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()  // Allow all requests
                )
                .headers(headers -> headers.frameOptions().disable());  // For H2 console

        return http.build();
    }
}