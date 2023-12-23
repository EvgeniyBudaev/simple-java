CREATE TABLE IF NOT EXISTS catalogs
(
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    alias      VARCHAR   NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    deleted    BOOL      NOT NULL,
    enabled    BOOL      NOT NULL,
    image      VARCHAR,
    name       VARCHAR   NOT NULL UNIQUE,
    updated_at TIMESTAMP NOT NULL,
    uuid       UUID      NOT NULL UNIQUE
);