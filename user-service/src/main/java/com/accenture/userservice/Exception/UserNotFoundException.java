package com.accenture.userservice.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
     super(message);
    }
}
