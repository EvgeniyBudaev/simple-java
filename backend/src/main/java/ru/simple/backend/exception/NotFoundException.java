package ru.simple.backend.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    private static final HttpStatus STATUS_CODE = HttpStatus.NOT_FOUND;

    public NotFoundException(String userMessage, String developMessage) {
        super(STATUS_CODE, userMessage, developMessage);
    }
}
