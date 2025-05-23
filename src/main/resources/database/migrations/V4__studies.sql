CREATE TABLE studies (
    id                   BLOB     NOT NULL,
    patients_id          BLOB     NOT NULL,
    devices_id           BLOB     NOT NULL,
    modality             TEXT     NOT NULL,
    instance_id          TEXT     NULL,
    accession_id         TEXT     NULL,
    date                 TEXT     NULL,
    description          TEXT     NULL,
    reason               TEXT     NULL,
    requested_procedure  TEXT     NULL,
    performed_procedure  TEXT     NULL,
    institution          TEXT     NULL,
    department           TEXT     NULL,
    station              TEXT     NULL,
    physicians           TEXT     NULL,
    operators            TEXT     NULL,
    height               INTEGER  NULL,
    weight               INTEGER  NULL,
    body_mass_index      REAL     NULL,
    age                  INTEGER  NULL,
    pregnancy            INTEGER  NULL,
    events               INTEGER  NULL,
    comment              TEXT     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (patients_id) REFERENCES patients (id),
    FOREIGN KEY (devices_id) REFERENCES devices (id),
    UNIQUE (instance_id),
    UNIQUE (accession_id)
);

CREATE TABLE studies_computed_tomographies (
    id                   BLOB     NOT NULL,
    studies_id           BLOB     NOT NULL,
    events               INTEGER  NULL,
    dose_length_product  REAL     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_mammographies (
    id                   BLOB     NOT NULL,
    studies_id           BLOB     NOT NULL,
    dose_length_product  REAL     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_fluoroscopies (
    id                   BLOB     NOT NULL,
    studies_id           BLOB     NOT NULL,
    events               INTEGER  NULL,
    dose_length_product  REAL     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_radiographies (
    id                   BLOB     NOT NULL,
    studies_id           BLOB     NOT NULL,
    events               INTEGER  NULL,
    dose_length_product  REAL     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_nuclear_medicines (
    id                   BLOB     NOT NULL,
    studies_id           BLOB     NOT NULL,
    events               INTEGER  NULL,
    dose_length_product  REAL     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

