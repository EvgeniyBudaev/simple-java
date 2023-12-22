package ru.simple.backend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CatalogEntity {
    private Long id;
    private String alias;
    private LocalDateTime createdAt;
    private Boolean deleted;
    private Boolean enabled;
    private String image;
    private String name;
    private LocalDateTime updatedAt;
    private String uuid;
}
