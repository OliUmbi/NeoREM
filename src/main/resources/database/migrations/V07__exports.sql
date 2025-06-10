CREATE TABLE exports (
    id            BLOB  NOT NULL,
    execution_id  BLOB  NOT NULL,
    type          TEXT  NOT NULL,
    filters       TEXT  NOT NULL,
    location      TEXT  NULL,

    PRIMARY KEY (id)
);
