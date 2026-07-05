CREATE TABLE role_permissions
(
    id BINARY(16) PRIMARY KEY,

    role_id BINARY(16) NOT NULL,

    permission_id BINARY(16) NOT NULL,

    is_active BOOLEAN NOT NULL,

    created_at DATETIME NOT NULL,

    updated_at DATETIME,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT fk_role_permission_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id),

    CONSTRAINT fk_role_permission_permission
        FOREIGN KEY (permission_id)
        REFERENCES permissions(id),

    CONSTRAINT uk_role_permission
        UNIQUE(role_id, permission_id)
);
