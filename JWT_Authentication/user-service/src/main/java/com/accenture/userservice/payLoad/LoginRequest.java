package com.accenture.userservice.payLoad;

public record LoginRequest(
        String email,
        String password
) {
}