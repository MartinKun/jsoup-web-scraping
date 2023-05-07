package com.example.simplewebscraper.controller;

import com.example.simplewebscraper.controller.response.ExceptionResponse;
import com.example.simplewebscraper.exception.ScrapeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ScrapeException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
            ScrapeException ex
    ) {
        String message = ex.getMessage();
        ExceptionResponse response = ExceptionResponse.builder()
                .message(message)
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
