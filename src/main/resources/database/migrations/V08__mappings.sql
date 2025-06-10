CREATE TABLE mappings (
    id        BLOB  NOT NULL,
    modality  TEXT  NOT NULL,
    name      TEXT  NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE mappings_protocols (
    id          BLOB  NOT NULL,
    mapping_id  BLOB  NOT NULL,
    value       TEXT  NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE mappings_descriptions (
    id          BLOB  NOT NULL,
    mapping_id  BLOB  NOT NULL,
    value       TEXT  NOT NULL,

    PRIMARY KEY (id)
);
