CREATE SCHEMA if NOT EXISTS recommendation;

CREATE TABLE recommendation.users
(
    outer_id BIGINT PRIMARY KEY,
    model_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE recommendation.items
(
    outer_id   BIGINT PRIMARY KEY,
    model_id   BIGINT NOT NULL UNIQUE,
    is_private BOOLEAN DEFAULT FALSE
);

CREATE TABLE recommendation.user_item -- interaction
(
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,

    CONSTRAINT user_item_pk PRIMARY KEY (user_id, item_id)
);

CREATE TABLE recommendation.item_vectors
(
    item_id BIGINT REFERENCES recommendation.items (model_id),
    vector  BYTEA NOT NULL
);

CREATE TABLE recommendation.user_vectors
(
    user_id BIGINT REFERENCES recommendation.users (model_id),
    vector  BYTEA NOT NULL
);
