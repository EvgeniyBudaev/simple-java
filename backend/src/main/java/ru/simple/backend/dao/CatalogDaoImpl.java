package ru.simple.backend.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.simple.backend.dto.catalog.request.RequestCatalogCreateDto;
import ru.simple.backend.dto.catalog.request.RequestCatalogUpdateDto;
import ru.simple.backend.exception.InternalServerException;
import ru.simple.backend.exception.NotFoundException;
import ru.simple.backend.model.CatalogEntity;
import ru.simple.backend.model.PaginationEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class CatalogDaoImpl implements CatalogDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_CATALOG =
            "INSERT INTO catalogs (alias, created_at, deleted, enabled, image, name, updated_at, uuid)\n"
                    + "VALUES (:alias, :createdAt, :deleted, :enabled, :image, :name, :updatedAt, cast(:uuid as uuid))";

    private static final String DELETE_CATALOG =
            "UPDATE catalogs\n" +
                    "SET deleted = :deleted,\n" +
                    "updated_at = :updatedAt\n" +
                    "WHERE uuid = cast(:uuid as uuid) AND deleted = false";

    private static final String UPDATE_CATALOG =
            "UPDATE catalogs\n" +
                    "SET alias = :alias,\n" +
                    "enabled = :enabled,\n" +
                    "image = :image,\n" +
                    "name = :name,\n" +
                    "updated_at = :updatedAt\n" +
                    "WHERE uuid = cast(:uuid as uuid) AND deleted = false";

    private static final String GET_CATALOG_BY_UUID =
            "SELECT id, alias, created_at, deleted, enabled, image, name, updated_at, uuid\n"
                    + "FROM catalogs\n"
                    + "WHERE uuid = cast(:uuid as uuid) AND deleted = false";

    private static final String GET_CATALOG_LIST =
            "SELECT id, alias, created_at, deleted, enabled, image, name, updated_at, uuid\n"
                    + "FROM catalogs\n" +
                    "WHERE deleted = false\n" +
                    "ORDER BY updated_at DESC\n" +
                    "LIMIT :limit OFFSET :offset";

    private static final String GET_TOTAL_ITEMS_BY_CATALOG_LIST =
            "SELECT COUNT(*)\n"
                    + "FROM catalogs\n" +
                    "WHERE deleted = false";

    public CatalogDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public CatalogEntity create(RequestCatalogCreateDto requestCatalogCreateDto) {
        try {
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
        } catch (Exception e) {
            throw new InternalServerException(
                    "Server error",
                    "Catalog with name: " + requestCatalogCreateDto.getName() +
                            " could not be created. Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public CatalogEntity delete(String uuid) {
        try {
            CatalogEntity catalog = findByUuid(uuid);
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("deleted", true)
                    .addValue("updatedAt", LocalDateTime.now())
                    .addValue("uuid", uuid);
            namedParameterJdbcTemplate.update(DELETE_CATALOG, parameters);
            catalog.setDeleted(true);
            catalog.setUpdatedAt(LocalDateTime.now());
            return catalog;
        } catch (Exception e) {
            throw new InternalServerException(
                    "Server error",
                    "Catalog with uuid: " + uuid + " could not be deleted. Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public CatalogEntity update(RequestCatalogUpdateDto requestCatalogUpdateDto) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("alias", requestCatalogUpdateDto.getAlias())
                    .addValue("enabled", requestCatalogUpdateDto.getEnabled())
                    .addValue("image", requestCatalogUpdateDto.getImage())
                    .addValue("name", requestCatalogUpdateDto.getName())
                    .addValue("updatedAt", LocalDateTime.now())
                    .addValue("uuid", UUID.fromString(requestCatalogUpdateDto.getUuid()));
            namedParameterJdbcTemplate.update(UPDATE_CATALOG, parameters);
            return findByUuid(requestCatalogUpdateDto.getUuid());
        } catch (Exception e) {
            throw new InternalServerException(
                    "Server error",
                    "Catalog with uuid: " + requestCatalogUpdateDto.getUuid() +
                            " could not be updated. Error: " + e.getMessage());
        }
    }

    @Override
    public CatalogEntity findByUuid(String uuid) {
        try {
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
                            .build());
        } catch (Exception e) {
            throw new NotFoundException(
                    "Catalog not found",
                    "Catalog with uuid: " + uuid + " not found. Error: " + e.getMessage());
        }
    }

    @Override
    public PaginationEntity<List<CatalogEntity>> getList(Integer page, Integer limit) {
        try {
            Integer offset = (page - 1) * limit;
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("limit", limit)
                    .addValue("offset", offset);
            // Getting a paginated list of catalogs
            List<CatalogEntity> catalogList = new ArrayList<>(namedParameterJdbcTemplate
                    .query(GET_CATALOG_LIST, params, (resultSet, i) -> CatalogEntity.builder()
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
            // Getting the total number of elements
            Integer totalItems = namedParameterJdbcTemplate.queryForObject(
                    GET_TOTAL_ITEMS_BY_CATALOG_LIST,
                    params,
                    Integer.class);

            PaginationEntity<List<CatalogEntity>> paginationEntity = new PaginationEntity<>(page, limit, totalItems);
            paginationEntity.setContent(Collections.singletonList(catalogList));
            return paginationEntity;
        } catch (Exception e) {
            throw new InternalServerException(
                    "Server error",
                    "Server error: " + e.getMessage());
        }
    }
}
