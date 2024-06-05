package com.example.projectmanagement.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(Management -> Management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())// for login and signup we do not need
                // the to be authenticated
                //only after the login and the signup we need the JWT token
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // the jwt token is validated or not
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {

         @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
             CorsConfiguration config = new CorsConfiguration();
             config.setAllowedOrigins(
                     Arrays.asList(
                             "http://localhost:3000",
                             "http://localhost:5173",
                             "http://localhost:4200"
                     )
             );
             config.setAllowedMethods(Collections.singletonList("*"));// ________1________. below
             config.setAllowCredentials(true);// _______2______below
             config.setAllowedHeaders(Collections.singletonList("*"));//________3______-
             config.setExposedHeaders(Arrays.asList("Authorization"));//_______4______
             config.setMaxAge(3600L);//___________5_____________
             return config ;
         }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
//__________1______________
//config.setAllowedMethods(Collections.singletonList("*")); sets the allowed HTTP methods for CORS requests to all methods using a wildcard,
// although it's more common and recommended to specify the allowed methods explicitly.
//_________2____________
//The cfg.setAllowCredentials(true); method is part of the CORS (Cross-Origin Resource Sharing) configuration.
// It is used to indicate whether the browser should include
// credentials (such as cookies, HTTP authentication, and client-side SSL certificates) in cross-origin requests.
//  __________What is credentials________
//Credentials: These include cookies, authorization headers, or TLS client certificates.
// Credentials are important for sessions and authentication.
//________________3________________
//setAllowedHeaders
//Purpose: Specifies which HTTP headers can be included in the actual request sent by the client to the server.
//_____________4______________
//setExposedHeaders
//Purpose: Specifies which headers are safe to be exposed to the client from the response.
//_____________5_____________
//By calling config.setMaxAge(3600L);, you are instructing the client's browser
// to cache the preflight response for 3600 seconds (1 hour). During this time, the browser
// will not send another preflight request for the same CORS request, thereby reducing the number
// of OPTIONS requests made to the server.
