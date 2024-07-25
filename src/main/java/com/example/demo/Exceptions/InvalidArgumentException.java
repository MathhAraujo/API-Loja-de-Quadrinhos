package com.example.demo.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class InvalidArgumentException extends RuntimeException {


    private LocalDateTime errorTime;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String message;
    private HttpStatus status;
    private Object rejectedValue;

    public InvalidArgumentException(String message, Object rejectedValue) {
        this.message = message;
        this.rejectedValue = rejectedValue;
        this.errorTime = LocalDateTime.now();
        this.status = HttpStatus.BAD_REQUEST;
    }

    public InvalidArgumentException(HttpStatus status, String message, Object rejectedValue) {
        this.message = message;
        this.rejectedValue = rejectedValue;
        this.errorTime = LocalDateTime.now();
        this.status = status;
    }

    @Override
    public String toString() {
        return "InvalidArgumentException{" +
                "\n errorTime= " + errorTime.format(format) +
                ",\n message= '" + message + '\'' +
                ",\n httpCode= " + status +
                ",\n rejectedValue= " + rejectedValue + "\n" +
                '}';
    }
}
