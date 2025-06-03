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
  ('Mountain Inn',  'Denver'),
  ('Riverside Hotel',    'Chicago'),
  ('City Center Lodge',  'Los Angeles');

-- table Customer
CREATE TABLE Customer (
    id      INT           NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255)  NOT NULL,
    email   VARCHAR(255)  NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- add customers
INSERT INTO Customer (name, email) VALUES
  ('Marek Novák',     'marek.novak@example.com'),
  ('Jana Kovačičová', 'jana.kovacikova@example.com'),
  ('Peter Horváth',   'peter.horvath@example.com'),
  ('Lucia Švecová',   'lucia.svecova@example.com'),
  ('Tomáš Bielik',    'tomas.bielik@example.com'),
  ('Martina Poláková','martina.polakova@example.com'),
  ('Richard Krajčovič','richard.krajcovic@example.com'),
  ('Zuzana Hrušovská','zuzana.hrusovska@example.com'),
  ('Martin Čech',     'martin.cech@example.com'),
  ('Eva Dvoráková',   'eva.dvorakova@example.com'),
  ('Katarína Procházková','katarina.prochazkova@example.com'),
  ('Jakub Šimko',     'jakub.simko@example.com'),
  ('Veronika Malá',   'veronika.mala@example.com'),
  ('Peter Blaško',    'peter.blasko@example.com'),
  ('Anna Varga',      'anna.varga@example.com');

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
-- Hotel 1 (Grand Plaza)
('101', 'Single',     1),
('102', 'Double',     1),
('103', 'Suite',      1),
-- Hotel 2 (Sea View Resort)
('201', 'Single',     2),
('202', 'Double',     2),
('203', 'Suite',      2),
-- Hotel 3 (Mountain Inn)
('301', 'Single',     3),
('302', 'Double',     3),
('303', 'Suite',      3),
-- Hotel 4 (Riverside Hotel)
('401', 'Single',     4),
('402', 'Double',     4),
('403', 'Suite',      4),
-- Hotel 5 (City Center Lodge)
('501', 'Single',     5),
('502', 'Double',     5),
('503', 'Suite',      5);

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
(1,  1,  '2025-06-01', '2025-06-05'),
(2,  2,  '2025-06-02', '2025-06-06'),
(3,  3,  '2025-06-03', '2025-06-07'),

(4,  4,  '2025-07-01', '2025-07-04'),
(5,  5,  '2025-07-02', '2025-07-05'),
(6,  6,  '2025-07-03', '2025-07-06'),

(7,  7,  '2025-08-01', '2025-08-05'),
(8,  8,  '2025-08-02', '2025-08-06'),
(9,  9,  '2025-08-03', '2025-08-07'),

(10, 10, '2025-09-01', '2025-09-05'),
(11, 11, '2025-09-02', '2025-09-06'),
(12, 12, '2025-09-03', '2025-09-07'),

(13, 13, '2025-10-01', '2025-10-04'),
(14, 14, '2025-10-02', '2025-10-05'),
(15, 15, '2025-10-03', '2025-10-06');