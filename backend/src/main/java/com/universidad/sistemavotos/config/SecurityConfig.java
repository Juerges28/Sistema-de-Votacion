package com.universidad.sistemavotos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para pruebas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",                  // base
                    "/index.html",       // Vite index
                    "/assets/**",        // Vite assets
                    "/oauth2/**",        // rutas necesarias de login
                    "/error"             // evita que redirija infinito
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> {
                oauth2.defaultSuccessUrl("/dashboard", true); // te redirige al inicio tras login
            });

        return http.build();
    }
}
