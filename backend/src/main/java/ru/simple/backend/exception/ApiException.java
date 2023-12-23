package ru.simple.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String userMessage;
    private final String developMessage;

    public ApiException(HttpStatus statusCode, String userMessage, String developMessage) {
        super(userMessage);
        this.statusCode = statusCode;
        this.developMessage = developMessage;
        this.userMessage = userMessage;
    }

}