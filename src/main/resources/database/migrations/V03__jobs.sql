CREATE TABLE jobs (
    id       BLOB     NOT NULL,
    task     TEXT     NOT NULL,
    cron     TEXT     NOT NULL,
    enabled  INTEGER  NOT NULL,

    PRIMARY KEY (id)
);
