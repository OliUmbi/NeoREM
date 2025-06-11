CREATE TABLE imports (
    id            BLOB     NOT NULL,
    execution_id  BLOB     NOT NULL,
    datetime      TEXT     NOT NULL,
    query         TEXT     NOT NULL,
    amount        INTEGER  NOT NULL,

    PRIMARY KEY (id)
);
