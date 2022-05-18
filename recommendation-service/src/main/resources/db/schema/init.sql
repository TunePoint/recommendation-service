CREATE SCHEMA if NOT EXISTS ${SCHEMA};

CREATE TABLE ${SCHEMA}.users
(
    outer_id BIGINT PRIMARY KEY,
    model_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE ${SCHEMA}.items
(
    outer_id BIGINT PRIMARY KEY,
    model_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE ${SCHEMA}.user_item -- interaction
(
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,

    CONSTRAINT user_item_pk PRIMARY KEY (user_id, item_id)
);

CREATE TABLE ${SCHEMA}.item_vectors
(
    item_id BIGINT REFERENCES ${SCHEMA}.items (model_id),
    vector  BYTEA NOT NULL
);

CREATE TABLE ${SCHEMA}.user_vectors
(
    user_id BIGINT REFERENCES ${SCHEMA}.users (model_id),
    vector  BYTEA NOT NULL
);
