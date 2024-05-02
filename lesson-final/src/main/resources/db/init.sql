CREATE SCHEMA IF NOT EXISTS lessonfinal;
ALTER ROLE admin IN DATABASE lessonfinal SET SEARCH_PATH=lessonfinal,public;
SET SEARCH_PATH=lessonfinal,public;

DROP INDEX IF EXISTS rooms_hotel_id_index;
DROP INDEX IF EXISTS bookings_room_id_start_index;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS hotels;
DROP TABLE IF EXISTS user_role_records;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

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

CREATE TABLE rooms(
    id BIGSERIAL PRIMARY KEY,
    name varchar (255) NOT NULL,
    description text NOT NULL,
    room_number integer NOT NULL,
    price decimal NOT NULL,
    max_capacity integer NOT NULL,
    hotel_id BIGINT NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT UK_rooms_hotel_id_room_number UNIQUE (hotel_id, room_number)
);

CREATE INDEX IF NOT EXISTS rooms_hotel_id_index
    ON rooms(hotel_id);

CREATE TABLE bookings(
    id BIGSERIAL PRIMARY KEY,
    start timestamptz NOT NULL,
    "end" timestamptz NOT NULL,
    room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS bookings_room_id_start_index
    ON bookings(room_id, start ASC);

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    CONSTRAINT UK_users_username UNIQUE (username),
    CONSTRAINT UK_users_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS user_roles(
    role_type varchar(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS user_role_records(
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_type varchar(255) NOT NULL REFERENCES user_roles(role_type) ON DELETE CASCADE,
    CONSTRAINT UK_user_role_records_user_id_role_id UNIQUE (user_id, role_type)
);

CREATE INDEX IF NOT EXISTS user_role_records_user_id_index
    ON user_role_records(user_id);