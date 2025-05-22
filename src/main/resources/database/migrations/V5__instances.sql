CREATE TABLE instances (
    id                  BLOB    NOT NULL,
    studies_id          BLOB    NOT NULL,
    modality            TEXT    NOT NULL,
    series_id           TEXT    NULL,
    instance_id         TEXT    NULL,
    date                TEXT    NULL,
    description         TEXT    NULL,
    protocol            TEXT    NULL,
    body_part           TEXT    NULL,
    comment             TEXT    NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (studies_id)    REFERENCES studies (id),
    UNIQUE (instance_id)
);
