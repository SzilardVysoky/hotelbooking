CREATE DATABASE IF NOT EXISTS hotelbookingdb
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE hotelbookingdb;

-- drop existing tables
DROP TABLE IF EXISTS RoomBooking;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Hotel;

-- drop & recreate application user
DROP USER IF EXISTS 'hoteluser'@'%';
CREATE USER 'hoteluser'@'%' IDENTIFIED BY 'hotelpass';
GRANT ALL PRIVILEGES ON hotelbookingdb.* TO 'hoteluser'@'%';
FLUSH PRIVILEGES;

-- table Hotel
CREATE TABLE Hotel (
    id           INT           NOT NULL AUTO_INCREMENT,
    name         VARCHAR(255)  NOT NULL,
    location     VARCHAR(255)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- add hotels
INSERT INTO Hotel (name, location) VALUES
  ('Grand Plaza',   'New York'),
  ('Sea View Resort','Miami'),
  ('Mountain Inn',  'Denver');

-- table Customer
CREATE TABLE Customer (
    id      INT           NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255)  NOT NULL,
    email   VARCHAR(255)  NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- add customers
INSERT INTO Customer (name, email) VALUES
  ('Alice Johnson',   'alice.johnson@example.com'),
  ('Bob Williams',    'bob.williams@example.com'),
  ('Carol Martinez',  'carol.martinez@example.com');

-- table Room
CREATE TABLE Room (
    id         INT           NOT NULL AUTO_INCREMENT,
    number     VARCHAR(50)   NOT NULL,
    room_type  VARCHAR(100)  NOT NULL,
    hotel_id   INT           NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_room_hotel  (hotel_id),
    CONSTRAINT fk_room_hotel
      FOREIGN KEY (hotel_id)
      REFERENCES Hotel(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
) ENGINE=InnoDB;

-- add rooms
INSERT INTO Room (number, room_type, hotel_id) VALUES
  ('101', 'Single',   1),
  ('102', 'Double',   1),
  ('201', 'Suite',    2),
  ('202', 'Single',   2),
  ('301', 'Double',   3);

-- table RoomBooking
CREATE TABLE RoomBooking (
    id            INT        NOT NULL AUTO_INCREMENT,
    room_id       INT        NOT NULL,
    customer_id   INT        NOT NULL,
    booking_date  DATE       NOT NULL,
    end_date      DATE       NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_rb_room     (room_id),
    INDEX idx_rb_customer (customer_id),
    CONSTRAINT chk_dates
      CHECK (booking_date < end_date),
    CONSTRAINT fk_rb_room
      FOREIGN KEY (room_id)
      REFERENCES Room(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
    CONSTRAINT fk_rb_customer
      FOREIGN KEY (customer_id)
      REFERENCES Customer(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
) ENGINE=InnoDB;

-- add bookings
INSERT INTO RoomBooking (room_id, customer_id, booking_date, end_date) VALUES
  (1, 1, '2025-06-01', '2025-06-05'),
  (2, 2, '2025-06-03', '2025-06-06'),
  (3, 3, '2025-07-10', '2025-07-15');