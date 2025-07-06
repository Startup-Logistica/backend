CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id                     UUID       PRIMARY KEY,
    name                   VARCHAR    NOT NULL,
    role                   SMALLINT   NOT NULL,
    password               VARCHAR    NOT NULL,
    email                  VARCHAR    NOT NULL UNIQUE,
    email_validated_at     TIMESTAMP,
    created_at             TIMESTAMP,
    disabled_at            TIMESTAMP,
    password_recovery_code VARCHAR(10)
)