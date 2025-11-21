# Hotelier Backend API

A complete Spring Boot backend application for a hotel management system where guests can access hotel information, place food orders, make room requests, and request pickup/drop services. All features are managed by admin users.

## Features

### Guest Features
- View hotel information (timings, WiFi password, Google Maps, important info)
- Place food orders
- Make room service requests
- Request pickup/drop services
- View their own orders and requests

### Admin Features
- Manage hotel information
- View and manage all food orders
- View and manage all room requests
- View and manage all pickup/drop requests
- Update status of orders and requests

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **MySQL Database**
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE hotelier_db;
```

Or the application will create it automatically if you have the right permissions.

### 2. Configuration

Update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Navigate to project directory
cd hotelier-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new guest
- `POST /api/auth/login` - Login (returns JWT token)

### Guest Endpoints (Requires GUEST role)

#### Hotel Info
- `GET /api/guest/hotel-info` - Get hotel information

#### Food Orders
- `POST /api/guest/food-orders` - Create a food order
- `GET /api/guest/food-orders` - Get my food orders
- `GET /api/guest/food-orders/{id}` - Get food order by ID

#### Room Requests
- `POST /api/guest/room-requests` - Create a room request
- `GET /api/guest/room-requests` - Get my room requests
- `GET /api/guest/room-requests/{id}` - Get room request by ID

#### Pickup/Drop
- `POST /api/guest/pickup-drop` - Create pickup/drop request
- `GET /api/guest/pickup-drop` - Get my pickup/drop requests
- `GET /api/guest/pickup-drop/{id}` - Get pickup/drop request by ID

### Admin Endpoints (Requires ADMIN role)

#### Hotel Info Management
- `GET /api/admin/hotel-info` - Get hotel information
- `PUT /api/admin/hotel-info` - Update hotel information

#### Food Orders Management
- `GET /api/admin/food-orders` - Get all food orders (optional: ?status=PENDING)
- `GET /api/admin/food-orders/{id}` - Get food order by ID
- `PUT /api/admin/food-orders/{id}/status` - Update food order status

#### Room Requests Management
- `GET /api/admin/room-requests` - Get all room requests (optional: ?status=PENDING)
- `GET /api/admin/room-requests/{id}` - Get room request by ID
- `PUT /api/admin/room-requests/{id}/status` - Update room request status

#### Pickup/Drop Management
- `GET /api/admin/pickup-drop` - Get all pickup/drop requests (optional: ?status=PENDING)
- `GET /api/admin/pickup-drop/{id}` - Get pickup/drop request by ID
- `PUT /api/admin/pickup-drop/{id}/status` - Update pickup/drop request status

## Authentication

All endpoints (except `/api/auth/**`) require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

## Sample Requests

### Register a Guest

```json
POST /api/auth/register
{
  "email": "guest@example.com",
  "password": "password123",
  "name": "John Doe",
  "roomNumber": "101",
  "phoneNumber": "+1234567890"
}
```

### Create Food Order

```json
POST /api/guest/food-orders
Authorization: Bearer <token>
{
  "item": "Pizza Margherita",
  "description": "Large pizza",
  "quantity": 1,
  "specialInstructions": "Extra cheese",
  "roomNumber": "101"
}
```

### Create Room Request

```json
POST /api/guest/room-requests
Authorization: Bearer <token>
{
  "requestType": "HOUSEKEEPING",
  "description": "Need room cleaning",
  "roomNumber": "101"
}
```

### Create Pickup/Drop Request

```json
POST /api/guest/pickup-drop
Authorization: Bearer <token>
{
  "serviceType": "PICKUP",
  "location": "Airport Terminal 1",
  "pickupDateTime": "2024-11-20T10:00:00",
  "numberOfPassengers": 2,
  "flightNumber": "AA123",
  "contactPhone": "+1234567890"
}
```

### Update Food Order Status (Admin)

```json
PUT /api/admin/food-orders/1/status
Authorization: Bearer <admin_token>
{
  "status": "DELIVERED"
}
```

## Database Schema

The application uses JPA with Hibernate, which will automatically create tables based on the entity models:

- `users` - User accounts (guests and admins)
- `hotel_info` - Hotel information
- `food_orders` - Food orders
- `room_requests` - Room service requests
- `pickup_drops` - Pickup/drop requests

## Creating an Admin User

To create an admin user, you can either:

1. Manually insert into the database:
```sql
INSERT INTO users (email, password, name, role, created_at, updated_at) 
VALUES ('admin@hotel.com', '$2a$10$...', 'Admin User', 'ADMIN', NOW(), NOW());
```

2. Or modify the registration endpoint to allow admin registration (for development only)

## CORS Configuration

The application is configured to accept requests from:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)

Update `app.cors.allowed-origins` in `application.properties` to add more origins.

## Project Structure

```
hotelier-backend/
├── src/
│   ├── main/
│   │   ├── java/com/hotelier/
│   │   │   ├── controller/     # REST Controllers
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── exception/      # Exception Handlers
│   │   │   ├── model/          # Entity Models
│   │   │   ├── repository/     # JPA Repositories
│   │   │   ├── security/       # Security Configuration
│   │   │   └── service/        # Business Logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## License

This project is created for educational purposes.

