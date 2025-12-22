package com.cristian.inventory.controller;

import com.cristian.inventory.exceptions.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "message", ex.getMessage()
                ));
    }
}
