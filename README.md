# Hotel Booking System

## Table of Contents
1. [Project Overview](#project-overview)
2. [ER Diagram & Relational Model](#er-diagram--relational-model)
3. [Database Setup](#database-setup)
4. [Application Configuration](#application-configuration)
5. [Build](#build)
6. [Running the Application](#running-the-application)
7. [Documentation & Authentication](#documentation--authentification)
8. [API Endpoints](#api-usage-examples)
   - [Hotels](#hotels)
   - [Rooms](#rooms)
   - [Customers](#customers)
   - [Bookings](#bookings)

---

## Project Overview

This **Hotel Booking System** is a simple RESTful web application built with Java 21, Spring Boot, Hibernate (JPA), and MySQL. It allows you to manage:

- **Hotels** (name, location)
- **Rooms** (number, type, which hotel they belong to)
- **Customers** (name, email)
- **Bookings** (which room, which customer, start/end dates)

Every entity supports full CRUD (Create, Read, Update, Delete) operations via HTTP endpoints. Bookings perform conflict-checking so that no two bookings overlap for the same room.

Key features:
- **Hotel–Room Relationship (1 : N)**  
  Each hotel can have many rooms.
- **Room–Customer Relationship (M : N)**  
  A many-to-many relationship between rooms and customers is implemented via the **RoomBooking** join table. A `RoomBooking` record represents one customer booking a specific room for a date range.
- **Date-Conflict Validation**  
  When creating or updating a booking, the service checks for overlapping date ranges and rejects conflicting bookings.
- **OpenAPI/Swagger Documentation**  
  All endpoints are documented via SpringDoc OpenAPI and exposed at `/swagger-ui/index.html`.
- **Basic HTTP Authentication**  
  All endpoints require basic authentication.

---

## ER Diagram & Relational Model

Below is a description of the ER (Entity–Relationship) diagram. The PDF diagram is committed at:
**src/main/resources/scripts/HotelBSDiagram.pdf**

- **Hotel** (`id`, `name`, `location`)
   - *Primary Key:* `id`
   - *1 : N* relationship to `Room` (a hotel can have many rooms).

- **Room** (`id`, `number`, `room_type`, `hotel_id`)
   - *Primary Key:* `id`
   - *Foreign Key:* `hotel_id` references `Hotel.id`
   - *1 : N* relationship to `RoomBooking` (one room can appear in many bookings).

- **Customer** (`id`, `name`, `email`)
   - *Primary Key:* `id`
   - *1 : N* relationship to `RoomBooking` (one customer can make many bookings).

- **RoomBooking** (`id`, `room_id`, `customer_id`, `booking_date`, `end_date`)
   - *Primary Key:* `id`
   - *Foreign Keys:*
      - `room_id` → `Room.id`
      - `customer_id` → `Customer.id`
   - Implements the **M : N** between `Room` and `Customer`.
   - Includes a `CHECK` constraint to ensure `booking_date < end_date`.

---

## Requirements
- Java 21
- Maven 3.x
- MySQL 8.x (or compatible)

## Database Setup
1. Start your MySQL server (e.g. via WampServer).
2. Execute the schema & seed script:
   ```bash
   mysql -u root < src/main/resources/scripts/hoteldb-schema.sql

By default, the script:

Drops existing tables (RoomBooking, Room, Customer, Hotel) if they exist.

Recreates tables with the correct columns, constraints, and foreign keys.

Inserts sample data:

- 5 Hotels

- 15 Customers

- 15 Rooms (3 per Hotel)

- 15 Bookings (one for each of the first 15 customers/rooms)

## Application Configuration
Configuration is handled via src/main/resources/application.properties. If you changed your MySQL credentials or port, update these properties:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/hotelbookingdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=hoteluser
   spring.datasource.password=hotelpass

   spring.jpa.hibernate.ddl-auto=validate
   spring.jpa.show-sql=true
   
   springdoc.api-docs.path=/v3/api-docs
   springdoc.swagger-ui.path=/swagger-ui.html
```
## Build
From the project root:
- mvn clean install

## Running the Application
- mvn spring-boot:run

- The app will start on http://localhost:8080

## Documentation & Authentification
- **ER Diagram:** src/main/resources/scripts/HotelBSDiagram.pdf

- **SQL Script:** src/main/resources/scripts/hoteldb-schema.sql

**Swagger UI (authorization):**
To access Swagger UI and API endpoints, use the default login credentials defined in SecurityConfig.java
   ```bash
http://localhost:8080/swagger-ui/index.html
```
- **Username** kuser
- **Password** kakademia

## API Usage Examples

## Hotels
   - List all Hotels
   ```bash
curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/hotels"
```

   - Get a Specific Hotel
   ```bash
curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/hotels/3"
```

   - Create a New Hotel
   ```bash
curl -u kuser:kakademia \
     -X POST "http://localhost:8080/api/hotels" \
     -H "Content-Type: application/json" \
     -d '{
           "name":     "Lakeside Retreat",
           "location": "Las Vegas"
         }'
```

   - Update an Existing Hotel
   ```bash
   curl -u kuser:kakademia \
     -X PUT "http://localhost:8080/api/hotels/6" \
     -H "Content-Type: application/json" \
     -d '{
           "name":     "Lakeside Retreat Deluxe",
           "location": "Kosice"
         }'
```

   - Delete a Hotel
   ```bash
   curl -u kuser:kakademia \
     -X DELETE "http://localhost:8080/api/hotels/6"
```

## Rooms
   - List All Rooms
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/rooms"
```

   - List Rooms by Hotel
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/rooms?hotelId=1"
```

   - Get a Specific Room
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/rooms/1"
```

   - Create a New Room
   ```bash
   curl -u kuser:kakademia \
     -X POST "http://localhost:8080/api/rooms" \
     -H "Content-Type: application/json" \
     -d '{
           "number":   "601",
           "roomType": "Single",
           "hotel":    { "id": 1 }
         }'
```

   - Update an Existing Room
   ```bash
   curl -u kuser:kakademia \
     -X PUT "http://localhost:8080/api/rooms/10" \
     -H "Content-Type: application/json" \
     -d '{
           "number":   "602",
           "roomType": "Suite",
           "hotel":    { "id": 2 }
         }'
```

   - Delete a Room
   ```bash
   curl -u kuser:kakademia \
     -X DELETE "http://localhost:8080/api/rooms/10"
```

## Customers
   - List All Customers
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/customers"
```

   - Get a Specific Customer
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/customers/13"
```

   - Create a New Customer
   ```bash
   curl -u kuser:kakademia \
     -X POST "http://localhost:8080/api/customers" \
     -H "Content-Type: application/json" \
     -d '{
           "name":  "Szilard Vysoky",
           "email": "szilard.vysoky@gmail.com"
         }'
```

   - Update an Existing Customer
   ```bash
   curl -u kuser:kakademia \
     -X PUT "http://localhost:8080/api/customers/10" \
     -H "Content-Type: application/json" \
     -d '{
           "name":  "Szilard Vysoky Jr.",
           "email": "szilard.vysoky.jr@example.com"
         }'
```

   - Delete a Customer
   ```bash
   curl -u kuser:kakademia \
     -X DELETE "http://localhost:8080/api/customers/10"
```

## Bookings
   - List All Bookings
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/bookings"
```

   - List Bookings by Customer
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/bookings?customerId=10"
```

   - List Bookings by Room
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/bookings?roomId=1"
```

   - Get a Specific Booking
   ```bash
   curl -u kuser:kakademia \
     -X GET "http://localhost:8080/api/bookings/1"
```

   - Create a New Booking
   ```bash
   curl -u kuser:kakademia \
     -X POST "http://localhost:8080/api/bookings" \
     -H "Content-Type: application/json" \
     -d '{
           "room":       { "id": 2 },
           "customer":   { "id": 1 },
           "bookingDate":"2025-08-01",
           "endDate":    "2025-08-05"
         }'
```

   - Update an Existing Booking
   ```bash
   curl -u kuser:kakademia \
     -X PUT "http://localhost:8080/api/bookings/1" \
     -H "Content-Type: application/json" \
     -d '{
           "roomId":     2,
           "customerId": 14,
           "bookingDate":"2024-12-29",
           "endDate":    "2025-01-01"
         }'
```

   - Delete a Booking
   ```bash
   curl -u kuser:kakademia \
     -X DELETE "http://localhost:8080/api/bookings/1"
```




