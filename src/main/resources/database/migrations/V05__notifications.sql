CREATE TABLE notifications (
    id         BLOB     NOT NULL,
    datetime   TEXT     NOT NULL,
    status     TEXT     NOT NULL,
    attempts   INTEGER  NOT NULL,
    recipient  TEXT     NOT NULL,
    subject    TEXT     NOT NULL,
    message    TEXT     NOT NULL,

    PRIMARY KEY (id)
);
