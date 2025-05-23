CREATE TABLE instances (
    id           BLOB  NOT NULL,
    studies_id   BLOB  NOT NULL,
    modality     TEXT  NOT NULL,
    series_id    TEXT  NULL,
    instance_id  TEXT  NULL,
    date         TEXT  NULL,
    description  TEXT  NULL,
    protocol     TEXT  NULL,
    comment      TEXT  NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id) REFERENCES studies (id),
    UNIQUE (instance_id)
);

CREATE TABLE instances_computed_tomographies (
    instances_id            BLOB  NOT NULL,
    type                    TEXT  NULL,
    dose_index_volume       REAL  NULL,
    dose_length_product     REAL  NULL,
    scanning_length         REAL  NULL,
    voltage_peak            REAL  NULL,
    tube_current            REAL  NULL,
    tube_current_max        REAL  NULL,
    exposure_time_rotation  REAL  NULL,
    pitch                   REAL  NULL,
    exposure_time           REAL  NULL,
    slice_thickness         REAL  NULL,
    collimation             REAL  NULL,
    modulation              TEXT  NULL,

    PRIMARY KEY (instances_id),
    FOREIGN KEY (instances_id) REFERENCES instances (id)
);

CREATE TABLE instances_mammographies (
    instances_id            BLOB  NOT NULL,
    laterality              TEXT  NULL,
    view                    TEXT  NULL,
    view_modifier           TEXT  NULL,
    compression_thickness   REAL  NULL,
    target                  TEXT  NULL,
    entrance_surface_dose   REAL  NULL,
    average_glandular_dose  REAL  NULL,
    exposure_control_mode   TEXT  NULL,
    exposure_control_mode   TEXT  NULL,

    PRIMARY KEY (instances_id),
    FOREIGN KEY (instances_id) REFERENCES instances (id)
);

CREATE TABLE instances_mammographies_filters (
    id                          BLOB  NOT NULL,
    instances_mammographies_id  BLOB  NOT NULL,
    material                    TEXT  NULL,
    voltage_peak                REAL  NULL,
    tube_current                REAL  NULL,
    exposure_time               REAL  NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (instances_mammographies_id) REFERENCES instances_mammographies (id)
);

CREATE TABLE instances_fluoroscopies (
    instances_id              BLOB  NOT NULL,
    type                      TEXT  NULL,
    pulse_rate                REAL  NULL,
    plane                     TEXT  NULL,
    dose_area_product         REAL  NULL,
    dose_reference_point      REAL  NULL,
    duration                  REAL  NULL,
    exposure_time             REAL  NULL,
    voltage_peak              REAL  NULL,
    tube_current              REAL  NULL,
    angle_primary             REAL  NULL,
    angle_secondary           REAL  NULL,
    detector_size             REAL  NULL,
    source_detector_distance  REAL  NULL,
    orientation               TEXT  NULL,

    PRIMARY KEY (instances_id),
    FOREIGN KEY (instances_id) REFERENCES instances (id)
);

CREATE TABLE instances_fluoroscopies_filters (
    id                          BLOB  NOT NULL,
    instances_fluoroscopies_id  BLOB  NOT NULL,
    material                    TEXT  NULL,
    thickness_min               REAL  NULL,
    thickness_max               REAL  NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (instances_fluoroscopies_id) REFERENCES instances_fluoroscopies (id)
);

CREATE TABLE instances_radiographies (
    instances_id            BLOB  NOT NULL,
    anatomical_structure    TEXT  NULL,
    laterality              TEXT  NULL,
    target_region           TEXT  NULL,
    image_view              TEXT  NULL,
    voltage_peak            REAL  NULL,
    tube_current            REAL  NULL,
    exposure_time           REAL  NULL,
    source_image_distance   REAL  NULL,
    grid_focal_distance     REAL  NULL,
    exposure_index          REAL  NULL,
    target_exposure_index   REAL  NULL,
    deviation_index         REAL  NULL,
    relative_xray_exposure  REAL  NULL,
    relative_exposure_unit  TEXT  NULL,
    dose_area_product       REAL  NULL,
    exposure_control_mode   TEXT  NULL,

    PRIMARY KEY (instances_id),
    FOREIGN KEY (instances_id) REFERENCES instances (id)
);

CREATE TABLE instances_radiographies_filters (
    id                          BLOB  NOT NULL,
    instances_radiographies_id  BLOB  NOT NULL,
    material                    TEXT  NULL,
    thickness_min               REAL  NULL,
    thickness_max               REAL  NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (instances_radiographies_id) REFERENCES instances_radiographies (id)
);

CREATE TABLE instances_nuclear_medicines (
    instances_id               BLOB     NOT NULL,
    radiopharmaceutical_agent  TEXT     NULL,
    radionuclide               TEXT     NULL,
    radionuclide_half_life     TEXT     NULL,
    administered_activity      TEXT     NULL,
    effective_dose             TEXT     NULL,
    associated_instances_id    BLOB     NULL, -- todo review
    associated_procedure       TEXT     NULL,
    radiopharmaceutical_start  TEXT     NULL,
    radiopharmaceutical_stop   TEXT     NULL,
    administration             TEXT     NULL,
    participant                TEXT     NULL,
    participant_role           TEXT     NULL,
    slices                     INTEGER  NULL,
    reconstruction_method      TEXT     NULL,
    coincidence_window_width   REAL     NULL,
    energy_window_lower        REAL     NULL,
    energy_window_upper        REAL     NULL,
    scan_progression           TEXT     NULL,
    rr_intervals               REAL     NULL,
    time_slots                 REAL     NULL,
    time_slices                REAL     NULL,
    finding_site               TEXT     NULL,
    laterality                 TEXT     NULL,
    organ_dose                 REAL     NULL,
    mass                       REAL     NULL,
    measurement_method         TEXT     NULL,
    reference_authority        TEXT     NULL,

    PRIMARY KEY (instances_id),
    FOREIGN KEY (instances_id) REFERENCES instances (id)
    FOREIGN KEY (associated_computed_tomographies_id) REFERENCES instances (id)
);
