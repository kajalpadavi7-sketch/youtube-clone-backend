package com.kajal.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class SecurityConfig {


    @Autowired
    private JwtFilter jwtFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http

        .cors(cors -> {})

        .csrf(csrf -> csrf.disable())


        .authorizeHttpRequests(auth -> auth


                // Public APIs

                .requestMatchers(
                        "/api/users/register",
                        "/api/users/login",
                        "/api/videos",
                        "/videos/**",
                        "/thumbnails/**"
                )
                .permitAll()



                // Protected API

                .requestMatchers(
                        "/api/videos/upload"
                )
                .authenticated()



                .anyRequest()
                .authenticated()


        )


        .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        )


        .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );



        return http.build();

    }




    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {


        return configuration.getAuthenticationManager();

    }


}