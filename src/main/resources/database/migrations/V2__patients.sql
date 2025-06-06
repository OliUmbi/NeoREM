CREATE TABLE patients (
    id               BLOB  NOT NULL,
    external_id      TEXT  NULL,
    external_issuer  TEXT  NULL,
    sex              TEXT  NULL,

    PRIMARY KEY (id)
);
