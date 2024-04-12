CREATE SCHEMA IF NOT EXISTS lesson4protected;
ALTER ROLE admin IN DATABASE lesson4protected SET SEARCH_PATH=lesson4protected,public;
SET SEARCH_PATH=lesson4protected,public;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role_records CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS posts_categories;
DROP TABLE IF EXISTS comments;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR (255) UNIQUE NOT NULL,
    email VARCHAR (255) UNIQUE NOT NULL,
    password VARCHAR (255) NOT NULL
);

CREATE TABLE role_records (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_type VARCHAR (255) not null,
    UNIQUE (user_id, role_type)
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR (255) UNIQUE NOT NULL
);

CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR (255) NOT NULL,
    description TEXT,
    body TEXT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone
);

CREATE TABLE posts_categories (
    posts_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    categories_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    UNIQUE (posts_id, categories_id)
);

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    body TEXT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone
);
