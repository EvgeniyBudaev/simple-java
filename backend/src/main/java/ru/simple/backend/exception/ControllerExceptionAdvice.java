package ru.simple.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.simple.backend.model.ErrorEntity;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity handleInternalServerException(InternalServerException e) {
        return error(e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorEntity handleNotFoundException(NotFoundException e) {
        return error(e);
    }

    private ErrorEntity error(ApiException e) {
        ErrorEntity error = new ErrorEntity();
        error.setStatusCode(e.getStatusCode().value());
        error.setServiceName("simple-service");
        error.setSuccess(false);
        error.setDeveloperMessage(e.getDevelopMessage());
        error.setUserMessage(e.getUserMessage());

        return error;
    }
}