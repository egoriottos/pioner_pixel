CREATE TABLE users (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255),
                       date_of_birth DATE,
                       password VARCHAR(255)
);

CREATE TABLE email_data (
                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            email VARCHAR(200) NOT NULL UNIQUE,
                            user_id BIGINT NOT NULL,
                            CONSTRAINT fk_email_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE phone_data (
                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            phone VARCHAR(13) UNIQUE,
                            user_id BIGINT NOT NULL,
                            CONSTRAINT fk_phone_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE account
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    balance NUMERIC(19, 2) NOT NULL,
    user_id BIGINT         NOT NULL UNIQUE,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)