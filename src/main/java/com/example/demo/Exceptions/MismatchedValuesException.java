package com.example.demo.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MismatchedValuesException extends RuntimeException {

    //Exceção não utilizada, estava retornando o stacktrace por algum motivo e não consegui remove-lo

    private LocalDateTime errorTime;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String message;
    private HttpStatus status;
    private Object value1;
    private Object value2;

    public MismatchedValuesException(HttpStatus status, String message, Object value1, Object value2) {
        this.status = status;
        this.message = message;
        this.value1 = value1;
        this.value2 = value2;
        this.errorTime = LocalDateTime.now();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return "MismatchException{" +
                "\n errorTime=" + errorTime.format(format) +
                ",\n message='" + message + '\'' +
                ",\n httpCode=" + status +
                ",\n mismatch=" + value1 + " =/= " + value2 + "\n" +
                '}';
    }
}
