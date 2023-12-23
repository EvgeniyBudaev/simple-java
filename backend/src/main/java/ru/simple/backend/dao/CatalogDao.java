package ru.simple.backend.dao;

import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.dto.catalog.request.RequestCatalogUpdateDto;
import ru.simple.backend.model.CatalogEntity;
import ru.simple.backend.model.PaginationEntity;

import java.util.List;

public interface CatalogDao {
    CatalogEntity create(RequestCatalogCreateDto requestCatalogCreateDto);

    CatalogEntity delete(String uuid);

    CatalogEntity update(RequestCatalogUpdateDto requestCatalogUpdateDto);

    CatalogEntity findByUuid(String uuid);

    PaginationEntity<List<CatalogEntity>> getList(Integer page, Integer limit);
}
