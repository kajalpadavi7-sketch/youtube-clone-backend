// package com.kajal.backend.security;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

// import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// import org.springframework.security.config.http.SessionCreationPolicy;

// import org.springframework.security.web.SecurityFilterChain;

// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.List;


// @Configuration
// public class SecurityConfig {


//     @Autowired
//     private JwtFilter jwtFilter;

//     @Autowired
// private CorsConfigurationSource corsConfigurationSource;


// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//     http
//         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//         .csrf(csrf -> csrf.disable())

//     .authorizeHttpRequests(auth -> auth

//     .requestMatchers(
//         "/",
//         "/error",
//         "/api/users",
//         "/api/users/register",
//         "/api/users/login",
//         "/api/videos",
//         "/api/videos/**",
//         "/videos/**",
//         "/thumbnails/**",
//         "/api/comments/video/**",
//         "/api/likes/**",
//         "/profiles/**",
//         "/api/users/{id}"
//     ).permitAll()

//     .requestMatchers(
//         "/api/videos/upload",
//         "/api/comments",
//         "/api/users/profile-image"
//     ).authenticated()

//     .anyRequest().authenticated()
// )
//         .sessionManagement(session ->
//                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//         )

//         .addFilterBefore(
//                 jwtFilter,
//                 UsernamePasswordAuthenticationFilter.class
//         );

//     return http.build();
// }



//     @Bean
//     public AuthenticationManager authenticationManager(
//             AuthenticationConfiguration configuration
//     ) throws Exception {


//         return configuration.getAuthenticationManager();

//     }
//     @Bean
// public CorsConfigurationSource corsConfigurationSource(){

//     CorsConfiguration configuration = new CorsConfiguration();
// configuration.setAllowedOrigins(
//         List.of(
//                 "http://localhost:5173",

//                 "https://youtube-clone-frontend-sigma-liard.vercel.app"
//         )
// );

//     configuration.setAllowedMethods(
//             List.of(
//                     "GET",
//                     "POST",
//                     "PUT",
//                     "DELETE",
//                     "OPTIONS"
//             )
//     );

//     configuration.setAllowedHeaders(
//             List.of("*")
//     );
//     configuration.setExposedHeaders(
//             List.of("Content-Range", "Accept-Ranges", "Content-Length", "Content-Type")
//     );

//     configuration.setAllowCredentials(true);


//     UrlBasedCorsConfigurationSource source =
//             new UrlBasedCorsConfigurationSource();


//     source.registerCorsConfiguration(
//             "/**",
//             configuration
//     );


//     return source;
// }


// }

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

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .cors(cors ->
                cors.configurationSource(corsConfigurationSource())
            )

            .csrf(csrf ->
                csrf.disable()
            )

            .authorizeHttpRequests(auth -> auth

                // =====================================
                // PUBLIC
                // =====================================

                .requestMatchers(
                    "/",
                    "/error",

                    "/api/users",
                    "/api/users/register",
                    "/api/users/login",

                    "/api/videos",
                    "/api/videos/**",

                    "/videos/**",
                    "/thumbnails/**",
                    "/profiles/**",

                    "/api/comments/video/**",
                    "/api/likes/**"
                ).permitAll()


                // =====================================
                // AUTHENTICATED
                // =====================================

                .requestMatchers(
                    "/api/videos/upload",
                    "/api/comments",
                    "/api/users/profile-image",
                    "/api/users/me"
                ).authenticated()


                // =====================================
                // OTHER USER PROFILE
                // =====================================

                .requestMatchers(
                    "/api/users/*"
                ).permitAll()


                // =====================================
                // EVERYTHING ELSE
                // =====================================

                .anyRequest().authenticated()
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


    // =========================================
    // AUTHENTICATION MANAGER
    // =========================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    // =========================================
    // CORS
    // =========================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
            List.of(
                "http://localhost:5173",
                "http://192.168.1.35:5173",
                "https://youtube-clone-frontend-sigma-liard.vercel.app"
            )
        );


        configuration.setAllowedMethods(
            List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
            )
        );


        configuration.setAllowedHeaders(
            List.of("*")
        );


        configuration.setExposedHeaders(
            List.of(
                "Content-Range",
                "Accept-Ranges",
                "Content-Length",
                "Content-Type"
            )
        );


        configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
            "/**",
            configuration
        );


        return source;
    }

}