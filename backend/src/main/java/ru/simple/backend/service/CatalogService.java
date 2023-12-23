package ru.simple.backend.service;

import org.springframework.stereotype.Service;
import ru.simple.backend.dao.CatalogDao;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.dto.catalog.request.RequestCatalogUpdateDto;
import ru.simple.backend.model.CatalogEntity;
import ru.simple.backend.model.PaginationEntity;

import java.util.List;

@Service
public class CatalogService {
    private final CatalogDao catalogDao;

    public CatalogService(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    public CatalogEntity create(RequestCatalogCreateDto requestCatalogCreateDto) {
        return catalogDao.create(requestCatalogCreateDto);
    }

    public CatalogEntity delete(String uuid) {
        return catalogDao.delete(uuid);
    }

    public CatalogEntity update(RequestCatalogUpdateDto requestCatalogUpdateDto) {
        return catalogDao.update(requestCatalogUpdateDto);
    }

    public CatalogEntity findByUuid(String uuid) {
        return catalogDao.findByUuid(uuid);
    }

    public PaginationEntity<List<CatalogEntity>> getList(Integer page, Integer limit) {
        return catalogDao.getList(page, limit);
    }
}
