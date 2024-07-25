package com.example.demo.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private LocalDateTime errorTime;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private HttpStatus status = HttpStatus.NOT_FOUND;
    private String message;
    private Long id;

    public ResourceNotFoundException(String message) {
        this.message = message;
        this.errorTime = LocalDateTime.now();

    }

    public ResourceNotFoundException(String message, Long id) {
        this.message = message;
        this.id = id;
        this.errorTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (id != null) {
            return "ResourceNotFoundException{" +
                    "\n errorTime= " + errorTime.format(format) +
                    ",\n message= '" + message + '\'' +
                    ",\n httpCode= " + status +
                    ",\n id= " + id + "\n" +
                    '}';
        } else {
            return "ResourceNotFoundException{" +
                    "\n errorTime= " + errorTime.format(format) +
                    ",\n message= '" + message + '\'' +
                    ",\n httpCode= " + status +
                    '}';
        }
    }
}
