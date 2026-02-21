package com.gateway.service;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@Configuration
public class AppConfig {

// we are using Spring Cloud Gateway Retry filter which is ✔ Recommended for Gateway (this is not resilience4j retry filter)
// and we are using spring cloud circuit breaker filter and it is using properties from yaml (resilence4j ) because Spring Cloud CircuitBreaker abstraction
//→ Backed by Resilience4j (because you added the dependency) but not retry

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()

                .route( "employee-service-route",
                        r -> r
                        .path("/gatewayEmp/**")
                        .filters(f -> f
                                .rewritePath("/gatewayEmp/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(config -> config
                                        .setName("employeeCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/employee")
                                )
                                .retry( retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(
                                                Duration.ofMillis(200),
                                                Duration.ofMillis(800),
                                                2,
                                                true
                                        )
                                )
                        )
                        .uri("lb://EMPLOYEE-SERVICE")  // <-- IMPORTANT



                )

                .route("department-service-route", r -> r
                        .path("/gatewayDept/**")
//                        .and()
//                        .after(afterDate)  // <-- Timeout after 5 seconds
                        .filters(f -> f
                                .rewritePath("/gatewayDept/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://DEPARTMENT-SERVICE")  // <-- IMPORTANT

                )

                .build();
    }
}
