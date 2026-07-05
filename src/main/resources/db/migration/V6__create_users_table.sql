CREATE TABLE users
(
    id BINARY(16) PRIMARY KEY,

    employee_code VARCHAR(20) NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100),

    email VARCHAR(150) NOT NULL UNIQUE,

    mobile VARCHAR(15),

    password VARCHAR(255) NOT NULL,

    department_id BINARY(16),

    designation VARCHAR(100),

    profile_image VARCHAR(255),

    last_login DATETIME,

    failed_login_attempts INT NOT NULL DEFAULT 0,

    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,

    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,

    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,

    enabled BOOLEAN NOT NULL DEFAULT TRUE,

    is_active BOOLEAN NOT NULL,

    created_at DATETIME NOT NULL,

    updated_at DATETIME,

    created_by VARCHAR(100),

    updated_by VARCHAR(100),

    CONSTRAINT fk_user_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
);