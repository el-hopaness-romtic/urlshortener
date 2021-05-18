CREATE TABLE url_map(
    short_url VARCHAR(10) NOT NULL,
    forward_url TEXT NOT NULL
);

CREATE UNIQUE INDEX ON url_map(short_url);
