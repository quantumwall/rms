DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS cargo;

DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS customer;

CREATE TABLE driver (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(100) NOT NULL);

CREATE TABLE manager (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(100) NOT NULL);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(100) UNIQUE NOT NULL);

CREATE TABLE route (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    shipment_date DATE NOT NULL,
    departure_city VARCHAR(100) NOT NULL,
    destination_city VARCHAR(100) NOT NULL,
    bill_number INTEGER,
    price NUMERIC(10, 2) NOT NULL,
    is_paid BOOLEAN NOT NULL,
    manager_id BIGINT REFERENCES manager ON DELETE SET NULL,
    driver_id BIGINT REFERENCES driver ON DELETE SET NULL,
    customer_id INTEGER REFERENCES customer ON DELETE SET NULL,
    UNIQUE(bill_number, manager_id));

CREATE TABLE cargo (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    route_id BIGINT REFERENCES route ON DELETE CASCADE NOT NULL,
    product TEXT NOT NULL,
    weight SMALLINT);

CREATE TABLE document (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name TEXT NOT NULL,
    path TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    route_id BIGINT REFERENCES route ON DELETE CASCADE NOT NULL );