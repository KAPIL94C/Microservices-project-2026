package com.gateway.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/employee")
    public Mono<String>  employeeCircuitBreakerFallback() {
        return Mono.just("Employee Service is currently unavailable. Please try again later.");
    }


}
