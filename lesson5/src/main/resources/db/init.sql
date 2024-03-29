CREATE SCHEMA IF NOT EXISTS lesson5;
ALTER ROLE admin IN DATABASE lesson5 SET SEARCH_PATH = lesson5,public;
SET SEARCH_PATH = lesson5,public;

DROP TABLE IF EXISTS categories;
CREATE TABLE IF NOT EXISTS categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS books;
CREATE TABLE IF NOT EXISTS books
(
    id          BIGSERIAL PRIMARY KEY,
    author      VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    category_id BIGINT       NOT NULL REFERENCES categories (id),
    CONSTRAINT UK_books_author_name UNIQUE (author, name)
);
