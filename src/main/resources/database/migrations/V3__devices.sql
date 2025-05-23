CREATE TABLE devices (
    id            BLOB  NOT NULL,
    manufacturer  TEXT  NULL,
    model         TEXT  NULL,
    serial        TEXT  NULL,
    software      TEXT  NULL,

    PRIMARY KEY (id)
);
