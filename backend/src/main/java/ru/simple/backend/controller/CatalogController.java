package ru.simple.backend.controller;

import org.springframework.web.bind.annotation.*;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.model.CatalogEntity;
import ru.simple.backend.service.CatalogService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/create")
    public CatalogEntity create(@RequestBody RequestCatalogCreateDto requestCatalogCreateDto) {
        return catalogService.create(requestCatalogCreateDto);
    }

    @GetMapping("/list")
    public Collection<CatalogEntity> getList() {
        return catalogService.getList();
    }
}
