CREATE TABLE users (
    id        BLOB  NOT NULL,
    name      TEXT  NOT NULL,
    password  TEXT  NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE users_roles (
    users_id  BLOB  NOT NULL,
    role      TEXT  NOT NULL,

    PRIMARY KEY (users_id, role),
    FOREIGN KEY (users_id) REFERENCES users (id)
);
