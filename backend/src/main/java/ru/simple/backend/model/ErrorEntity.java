package ru.simple.backend.model;

import lombok.Data;

@Data
public class ErrorEntity {
    private String serviceName;
    private Integer statusCode;
    private Boolean success;
    private String developMessage;
    private String userMessage;
}
