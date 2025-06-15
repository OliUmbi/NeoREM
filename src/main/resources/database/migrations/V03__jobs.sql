CREATE TABLE jobs (
    id       BLOB     NOT NULL,
    task     TEXT     NOT NULL,
    cron     TEXT     NOT NULL,
    enabled  INTEGER  NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE jobs_parameters (
    id       BLOB  NOT NULL,
    jobs_id  BLOB  NOT NULL,
    key      TEXT  NOT NULL,
    value    TEXT  NOT NULL,

    PRIMARY KEY (id)
);
