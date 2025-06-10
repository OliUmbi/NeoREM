CREATE TABLE executions (
    id      BLOB  NOT NULL,
    job_id  BLOB  NULL,
    start   TEXT  NULL,
    end     TEXT  NULL,
    task    TEXT  NOT NULL,
    status  TEXT  NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE executions_messages (
    id            BLOB  NOT NULL,
    execution_id  BLOB  NOT NULL,
    datetime      TEXT  NOT NULL,
    message       TEXT  NOT NULL,

    PRIMARY KEY (id)
);
