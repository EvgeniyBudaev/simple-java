package ru.simple.backend.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiException {
    private static final HttpStatus STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException(String userMessage, String developMessage) {
        super(STATUS_CODE, userMessage, developMessage);
    }
}
