CREATE SCHEMA IF NOT EXISTS lessonfinal;
ALTER ROLE admin IN DATABASE lessonfinal SET SEARCH_PATH=lessonfinal,public;
SET SEARCH_PATH=lessonfinal,public;

DROP INDEX IF EXISTS rooms_hotel_id_index;
DROP INDEX IF EXISTS bookings_room_id_start_index;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS rooms;
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
