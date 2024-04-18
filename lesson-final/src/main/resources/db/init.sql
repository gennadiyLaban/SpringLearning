CREATE SCHEMA IF NOT EXISTS lessonfinal;
ALTER ROLE admin IN DATABASE lessonfinal SET SEARCH_PATH=lessonfinal,public;
SET SEARCH_PATH=lessonfinal,public;

DROP TABLE IF EXISTS hotels;

CREATE TABLE hotels(
    id BIGSERIAL PRIMARY KEY,
    name varchar (255) NOT NULL,
    address_city varchar (255) NOT NULL,
    address_street varchar (255) NOT NULL,
    address_number varchar (255) NOT NULL,
    distance_from_center bigint NOT NULL,
    rating decimal NOT NULL default 0,
    number_of_rating integer NOT NULL default 0
);
