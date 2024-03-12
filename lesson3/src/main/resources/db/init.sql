CREATE SCHEMA IF NOT EXISTS lesson3;
ALTER ROLE admin IN DATABASE lesson3 SET SEARCH_PATH=lesson3,public;
SET SEARCH_PATH=lesson3,public;

DROP TABLE IF EXISTS contacts;
CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    email VARCHAR (100) NOT NULL,
    phone VARCHAR (50) NOT NULL
);