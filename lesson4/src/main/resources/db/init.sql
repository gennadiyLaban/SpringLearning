CREATE SCHEMA IF NOT EXISTS lesson4;
ALTER ROLE admin IN DATABASE lesson4 SET SEARCH_PATH=lesson4,public;
SET SEARCH_PATH=lesson4,public;

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL
);

DROP TABLE IF EXISTS categories CASCADE;
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR (255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS posts CASCADE;
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR (255) NOT NULL,
    description TEXT,
    body TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone
);

DROP TABLE IF EXISTS posts_categories;
CREATE TABLE posts_categories (
    posts_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    categories_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    UNIQUE (posts_id, categories_id)
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    body TEXT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone
);
