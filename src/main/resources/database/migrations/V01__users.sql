CREATE TABLE users (
    id        BLOB  NOT NULL,
    name      TEXT  NOT NULL,
    password  TEXT  NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE users_roles (
    user_id   BLOB  NOT NULL,
    role      TEXT  NOT NULL,

    PRIMARY KEY (user_id, role)
);
