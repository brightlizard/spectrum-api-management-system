CREATE TABLE IF NOT EXISTS apis (
    id varchar(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    version VARCHAR(255) NOT NULL,
    description text NOT NULL,
    specId VARCHAR(36) UNIQUE
);