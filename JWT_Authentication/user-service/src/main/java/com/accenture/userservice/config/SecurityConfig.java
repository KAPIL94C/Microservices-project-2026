package com.accenture.userservice.config;

import com.accenture.userservice.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/logout").permitAll()
//                        .requestMatchers(AppConstants.AUTH_ADMIN_URLS).hasRole(AppConstants.ADMIN_ROLE)
//                        .requestMatchers(AppConstants.AUTH_GUEST_URLS).hasRole(AppConstants.GUEST_ROLE)
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2.successHandler(successHandler)
                        .failureHandler(null))
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, e) -> {
                            //error message
//                    e.printStackTrace();
                            response.setStatus(401);
                            response.setContentType("application/json");
                            String message = e.getMessage();
//                            String error = (String) request.getAttribute("error");
//                            if (error != null) {
//                                message = error;
//                            }
                    Map<String, Object> errorMap = Map.of("message", message, "statusCode",404);
  //                          var apiError = ApiError.of(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access", message, request.getRequestURI(), true);
                            var objectMapper = new ObjectMapper();
                            response.getWriter().write(objectMapper.writeValueAsString(errorMap));
//                        })
//                .accessDeniedHandler((request, response, e) -> {
//
//                    response.setStatus(403);
//                    response.setContentType("application/json");
//                    String message = e.getMessage();
//                    String error = (String) request.getAttribute("error");
//                    if (error != null) {
//                        message = error;
//                    }
//                    var apiError = ApiError.of(HttpStatus.FORBIDDEN.value(), "Forbidden Access", message, request.getRequestURI(), true);
//                    var objectMapper = new ObjectMapper();
//                    response.getWriter().write(objectMapper.writeValueAsString(apiError));


                })).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
