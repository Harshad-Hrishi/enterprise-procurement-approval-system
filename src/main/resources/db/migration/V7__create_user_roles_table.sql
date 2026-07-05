CREATE TABLE user_roles
(
    id BINARY(16) PRIMARY KEY,

    user_id BINARY(16) NOT NULL,

    role_id BINARY(16) NOT NULL,

    assigned_at DATETIME NOT NULL,

    assigned_by VARCHAR(100),

    is_active BOOLEAN NOT NULL,

    created_at DATETIME NOT NULL,

    updated_at DATETIME,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT fk_user_role_user
        FOREIGN KEY(user_id)
        REFERENCES users(id),

    CONSTRAINT fk_user_role_role
        FOREIGN KEY(role_id)
        REFERENCES roles(id),

    CONSTRAINT uk_user_role
        UNIQUE(user_id, role_id)
);