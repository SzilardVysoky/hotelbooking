Hotel Booking System

## Project Description
A simple RESTful web application to manage hotels, rooms, customers, and room bookings.  
**Main features:**
- CRUD operations for Hotels, Rooms, Customers
- Book a room with date-conflict validation
- OpenAPI/Swagger UI documentation
- Secured endpoints (all except Swagger UI)

## Requirements
- Java 21
- Maven 3.x
- MySQL 8.x (or compatible)

## Database Setup
1. Start your MySQL server (e.g. via WampServer).
2. Execute the schema & seed script:
   ```bash
   mysql -u root < src/main/resources/scripts/hoteldb-schema.sql

## Application Configuration
Edit src/main/resources/application.properties if you changed DB credentials:
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

## Documentation
- ER Diagram: src/main/resources/scripts/HotelBSDiagram.pdf

- SQL Script: src/main/resources/scripts/hoteldb-schema.sql

Swagger UI (no auth):
   ```bash
http://localhost:8080/swagger-ui/index.html
```

## API Usage Examples
List all Hotels
   ```bash
curl http://localhost:8080/api/hotels
```

## Create a Customer
   ```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dave Peterson",
    "email": "dave.peterson@example.com"
  }'
```

## Create a Booking
   ```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "room": {"id": 2},
    "customer": {"id": 1},
    "bookingDate": "2025-08-01",
    "endDate":     "2025-08-05"
  }'
```

## Update Booking Dates
   ```bash
   curl -X PUT http://localhost:8080/api/bookings/1 \
  -H "Content-Type: application/json" \
  -d '{
    "room": {"id": 2},
    "customer": {"id": 1},
    "bookingDate": "2025-08-02",
    "endDate":     "2025-08-06"
  }'
```

## Fetch Rooms by Hotel
   ```bash
   curl http://localhost:8080/api/rooms?hotelId=1
```





