CREATE TABLE users
(
    id         BIGSERIAL     NOT NULL PRIMARY KEY,
    first_name VARCHAR(1024) NOT NULL,
    last_name  VARCHAR(1024) NOT NULL,
    admin      BOOLEAN       NOT NULL DEFAULT FALSE
);

INSERT INTO users(first_name, last_name, admin)
VALUES ('Jakub', 'Janecek', FALSE);
