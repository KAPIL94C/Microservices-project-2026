package com.accenture.departmentservice.Exception;

import com.accenture.departmentservice.Entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<?> handleDepartmentNotFoundException(DepartmentNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), e.getMessage(), "Department with ID does not exist");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
