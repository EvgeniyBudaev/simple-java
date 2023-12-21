package ru.simple.backend.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.model.CatalogEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CatalogDaoImpl implements CatalogDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_CATALOG =
            "INSERT INTO catalogs (alias, created_at, deleted, enabled, image, name, updated_at, uuid)\n"
            + "VALUES (:alias, :createdAt, :deleted, :enabled, :image, :name, :updatedAt, cast(:uuid as uuid))";

    private static final String GET_CATALOG_LIST =
            "SELECT *\n"
            + "from catalogs";

    public CatalogDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public CatalogEntity create(RequestCatalogCreateDto requestCatalogCreateDto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("alias", requestCatalogCreateDto.getAlias())
                .addValue("createdAt", LocalDateTime.now())
                .addValue("deleted", false)
                .addValue("enabled", true)
                .addValue("image", "default_image.jpg")
                .addValue("name", requestCatalogCreateDto.getName())
                .addValue("updatedAt", LocalDateTime.now())
                .addValue("uuid", java.util.UUID.randomUUID().toString());

        namedParameterJdbcTemplate.update(CREATE_CATALOG, parameters);
        CatalogEntity catalogEntity = namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM catalogs WHERE alias = :alias",
                parameters,
                (resultSet, i) -> CatalogEntity.builder()
                        .id(resultSet.getLong("id"))
                        .alias(resultSet.getString("alias"))
                        .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                        .deleted(resultSet.getBoolean("deleted"))
                        .enabled(resultSet.getBoolean("enabled"))
                        .image(resultSet.getString("image"))
                        .name(resultSet.getString("name"))
                        .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                        .uuid(resultSet.getString("uuid"))
                        .build()
        );

        return catalogEntity;
    }

    @Override
    public Collection<CatalogEntity> getList() {
        System.out.println("Controller getList");
        return namedParameterJdbcTemplate.query(GET_CATALOG_LIST, (resultSet, i) -> {
                    CatalogEntity catalogEntity = CatalogEntity.builder()
                            .id(resultSet.getLong("id"))
                            .alias(resultSet.getString("alias"))
                            .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                            .deleted(resultSet.getBoolean("deleted"))
                            .enabled(resultSet.getBoolean("enabled"))
                            .image(resultSet.getString("image"))
                            .name(resultSet.getString("name"))
                            .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                            .uuid(resultSet.getString("uuid"))
                            .build();

                    return catalogEntity;
                })
                .stream()
                .collect(Collectors.toList());
    }
}
