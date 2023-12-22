package ru.simple.backend.dto.catalog.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestCatalogUpdateDto {
    @NotNull
    private String alias;
    private Boolean enabled;
    private String image;
    @NotNull
    private String name;
    @NotNull
    private String uuid;
}
