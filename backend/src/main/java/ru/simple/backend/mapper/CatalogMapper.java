package ru.simple.backend.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.model.CatalogEntity;

@Mapper(componentModel = "spring")
@Component
public interface CatalogMapper {
    CatalogEntity mapRow(RequestCatalogCreateDto requestCatalogCreateDto);
}
