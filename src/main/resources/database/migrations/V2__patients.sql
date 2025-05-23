CREATE TABLE patients (
    id               BLOB  NOT NULL,
    external_id      TEXT  NOT NULL,
    external_issuer  TEXT  NULL,
    sex              TEXT  NULL,

    PRIMARY KEY (id)
);