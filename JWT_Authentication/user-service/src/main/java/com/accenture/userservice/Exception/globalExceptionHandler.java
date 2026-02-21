package com.accenture.userservice.Exception;


import com.accenture.userservice.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

//@RestControllerAdvice
//public class globalExceptionHandler {
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> handleDepartmentNotFoundException(UserNotFoundException e) {
//        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "User with ID does not exist");
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//}
