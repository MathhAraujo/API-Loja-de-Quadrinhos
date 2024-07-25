package com.example.demo.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MaxValueException extends RuntimeException {

    private LocalDateTime errorTime;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String message;
    private HttpStatus status;
    private Object maxValue;
    private Object inputValue;

    public MaxValueException(HttpStatus status, String message, Object maxValue, Object inputValue) {
        this.status = status;
        this.message = message;
        this.maxValue = maxValue;
        this.inputValue = inputValue;
        this.errorTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "MaxValueExceededException{" +
                "\n errorTime=" + errorTime.format(format) +
                ",\n message='" + message + '\'' +
                ",\n httpCode=" + status +
                ",\n maxValue=" + maxValue +
                ",\n inputValue=" + inputValue + "\n" +
                '}';
    }
}
