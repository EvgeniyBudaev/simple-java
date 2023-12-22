package ru.simple.backend.service;

import org.springframework.stereotype.Service;
import ru.simple.backend.dao.CatalogDao;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.dto.catalog.request.RequestCatalogUpdateDto;
import ru.simple.backend.model.CatalogEntity;

import java.util.Collection;

@Service
public class CatalogService {
    private final CatalogDao catalogDao;

    public CatalogService(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    public CatalogEntity create(RequestCatalogCreateDto requestCatalogCreateDto) {
        return catalogDao.create(requestCatalogCreateDto);
    }

    public CatalogEntity update(RequestCatalogUpdateDto requestCatalogUpdateDto) {
        return catalogDao.update(requestCatalogUpdateDto);
    }

    public CatalogEntity findByUuid(String uuid) {
        return catalogDao.findByUuid(uuid);
    }

    public Collection<CatalogEntity> getList() {
        return catalogDao.getList();
    }
}
