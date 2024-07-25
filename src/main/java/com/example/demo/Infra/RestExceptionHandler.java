package com.example.demo.Infra;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    private ResponseEntity resourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.toString());
    }

    @ExceptionHandler(value = InvalidArgumentException.class)
    private ResponseEntity invalidArgumentException(InvalidArgumentException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.toString());
    }
}
