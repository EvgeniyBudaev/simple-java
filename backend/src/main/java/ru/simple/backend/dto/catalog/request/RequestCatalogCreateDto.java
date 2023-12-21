package ru.simple.backend.dto.catalog.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestCatalogCreateDto {
    @NotNull
    private String alias;
    @NotNull
    private String name;
}
