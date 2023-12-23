package ru.simple.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.dto.catalog.request.RequestCatalogUpdateDto;
import ru.simple.backend.model.CatalogEntity;
import ru.simple.backend.service.CatalogService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/create")
    public ResponseEntity<CatalogEntity> create(@RequestBody RequestCatalogCreateDto requestCatalogCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.create(requestCatalogCreateDto));
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<CatalogEntity> delete(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.delete(uuid));
    }

    @PutMapping("/update")
    public ResponseEntity<CatalogEntity> update(@RequestBody RequestCatalogUpdateDto requestCatalogUpdateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.update(requestCatalogUpdateDto));
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<CatalogEntity> findByUuid(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.findByUuid(uuid));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CatalogEntity>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.getList());
    }
}
