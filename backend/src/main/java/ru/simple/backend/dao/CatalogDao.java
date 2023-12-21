package ru.simple.backend.dao;

import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.model.CatalogEntity;

import java.util.Collection;

public interface CatalogDao {
    CatalogEntity create(RequestCatalogCreateDto requestCatalogCreateDto);
    Collection<CatalogEntity> getList();
}
