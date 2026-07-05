CREATE TABLE roles
(
    id BINARY(16) PRIMARY KEY,

    name VARCHAR(100) NOT NULL UNIQUE,

    code VARCHAR(50) NOT NULL UNIQUE,

    description VARCHAR(255),

    priority INT NOT NULL,

    system_role BOOLEAN NOT NULL,

    is_active BOOLEAN NOT NULL,

    created_at DATETIME NOT NULL,

    updated_at DATETIME,

    created_by VARCHAR(100),

    updated_by VARCHAR(100)
);