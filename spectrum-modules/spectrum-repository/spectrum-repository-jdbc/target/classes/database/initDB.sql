CREATE TABLE IF NOT EXISTS apis (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    version VARCHAR(255) NOT NULL,
    description text NOT NULL,
    specId UUID UNIQUE
);