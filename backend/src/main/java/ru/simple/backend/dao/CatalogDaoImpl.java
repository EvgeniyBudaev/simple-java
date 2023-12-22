package ru.simple.backend.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.dto.catalog.request.RequestCatalogUpdateDto;
import ru.simple.backend.model.CatalogEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Component
public class CatalogDaoImpl implements CatalogDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_CATALOG =
            "INSERT INTO catalogs (alias, created_at, deleted, enabled, image, name, updated_at, uuid)\n"
            + "VALUES (:alias, :createdAt, :deleted, :enabled, :image, :name, :updatedAt, cast(:uuid as uuid))";

    private static final String UPDATE_CATALOG =
            "UPDATE catalogs\n" +
                    "SET alias = :alias,\n" +
                    "enabled = :enabled,\n" +
                    "image = :image,\n" +
                    "name = :name,\n" +
                    "updated_at = :updatedAt\n" +
                    "WHERE uuid = cast(:uuid as uuid)";

    private static final String GET_CATALOG_BY_UUID =
            "SELECT *\n"
            + "FROM catalogs\n"
            + "WHERE uuid = cast(:uuid as uuid)";

    private static final String GET_CATALOG_LIST =
            "SELECT *\n"
            + "FROM catalogs";

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
        return namedParameterJdbcTemplate.queryForObject(
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
    }

    @Transactional
    @Override
    public CatalogEntity update(RequestCatalogUpdateDto requestCatalogUpdateDto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("alias", requestCatalogUpdateDto.getAlias())
                .addValue("enabled", requestCatalogUpdateDto.getEnabled())
                .addValue("image", requestCatalogUpdateDto.getImage())
                .addValue("name", requestCatalogUpdateDto.getName())
                .addValue("updatedAt", LocalDateTime.now())
                .addValue("uuid", UUID.fromString(requestCatalogUpdateDto.getUuid()));

        namedParameterJdbcTemplate.update(UPDATE_CATALOG, parameters);
        return findByUuid(requestCatalogUpdateDto.getUuid());
    }

    @Override
    public CatalogEntity findByUuid(String uuid) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return namedParameterJdbcTemplate.queryForObject(
                GET_CATALOG_BY_UUID,
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
    }

    @Override
    public Collection<CatalogEntity> getList() {
        return new ArrayList<>(namedParameterJdbcTemplate.query(GET_CATALOG_LIST, (resultSet, i) -> CatalogEntity.builder()
                .id(resultSet.getLong("id"))
                .alias(resultSet.getString("alias"))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .deleted(resultSet.getBoolean("deleted"))
                .enabled(resultSet.getBoolean("enabled"))
                .image(resultSet.getString("image"))
                .name(resultSet.getString("name"))
                .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                .uuid(resultSet.getString("uuid"))
                .build()));
    }
}
