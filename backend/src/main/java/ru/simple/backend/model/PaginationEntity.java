package ru.simple.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class PaginationEntity<T> {
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer page;
    private Integer limit;
    private Integer totalItems;
    private List<T> content;

    public PaginationEntity(Integer page, Integer limit, Integer totalItems) {
        this.hasPrevious = page > 1;
        this.hasNext = (page * limit) < totalItems;
        this.page = page;
        this.limit = limit;
        this.totalItems = totalItems;
    }
}
