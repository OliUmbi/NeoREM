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
    pregnancy            TEXT     NULL,
    comment              TEXT     NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (patients_id) REFERENCES patients (id),
    FOREIGN KEY (devices_id) REFERENCES devices (id),
    UNIQUE (instance_id),
    UNIQUE (accession_id)
);

CREATE TABLE studies_computed_tomographies (
    studies_id           BLOB     NOT NULL,
    events               INTEGER  NULL,
    dose_length_product  REAL     NULL,

    PRIMARY KEY (studies_id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_fluoroscopies (
    studies_id                        BLOB  NOT NULL,
    dose_area_product_fluoroscopy     REAL  NULL,
    dose_area_product_acquisition     REAL  NULL,
    dose_area_product_total           REAL  NULL,
    dose_reference_point_fluoroscopy  REAL  NULL,
    dose_reference_point_acquisition  REAL  NULL,
    dose_reference_point_total        REAL  NULL,
    duration_fluoroscopy              REAL  NULL,
    duration_acquisition              REAL  NULL,
    reference_point_definition        TEXT  NULL,

    PRIMARY KEY (studies_id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_mammographies (
    studies_id                    BLOB     NOT NULL,
    average_glandular_dose_left   REAL     NULL,
    average_glandular_dose_right  REAL     NULL,

    PRIMARY KEY (studies_id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_nuclear_medicines (
    studies_id      BLOB  NOT NULL,
    effective_dose  REAL  NULL,
    agent           TEXT  NULL,
    radionuclide    TEXT  NULL,
    half_life       REAL  NULL,
    time_start      TEXT  NULL,
    time_end        TEXT  NULL,
    activity        REAL  NULL,
    route           TEXT  NULL,
    laterality      TEXT  NULL,

    PRIMARY KEY (studies_id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);

CREATE TABLE studies_radiographies (
    studies_id           BLOB     NOT NULL,
    events               INTEGER  NULL,
    dose_area_product    REAL     NULL,

    PRIMARY KEY (studies_id),
    FOREIGN KEY (studies_id) REFERENCES studies (id)
);
