# API Documentation

## Base URL
```
http://localhost:8080
```

## Authentication
All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

---

## Authentication Endpoints

### Register Guest
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "guest@example.com",
  "password": "password123",
  "name": "John Doe",
  "roomNumber": "101",
  "phoneNumber": "+1234567890"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "guest@example.com",
  "name": "John Doe",
  "role": "GUEST",
  "userId": 1
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "guest@example.com",
  "password": "password123"
}
```

**Response:** Same as Register

---

## Guest Endpoints

### Get Hotel Information
```http
GET /api/guest/hotel-info
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": 1,
  "importantInfo": "Welcome to our hotel!",
  "checkInTime": "14:00:00",
  "checkOutTime": "11:00:00",
  "breakfastTime": "07:00:00",
  "lunchTime": "12:00:00",
  "dinnerTime": "19:00:00",
  "receptionHours": "24/7",
  "googleMapUrl": "https://maps.google.com/...",
  "wifiPassword": "hotelwifi2024",
  "wifiNetworkName": "Hotel_Guest_WiFi"
}
```

### Create Food Order
```http
POST /api/guest/food-orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "item": "Pizza Margherita",
  "description": "Large pizza with mozzarella",
  "quantity": 1,
  "specialInstructions": "Extra cheese, no onions",
  "roomNumber": "101"
}
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "userEmail": "guest@example.com",
  "userName": "John Doe",
  "item": "Pizza Margherita",
  "description": "Large pizza with mozzarella",
  "quantity": 1,
  "specialInstructions": "Extra cheese, no onions",
  "status": "PENDING",
  "orderTime": "2024-11-16T12:00:00",
  "deliveryTime": null,
  "roomNumber": "101",
  "createdAt": "2024-11-16T12:00:00"
}
```

### Get My Food Orders
```http
GET /api/guest/food-orders
Authorization: Bearer <token>
```

### Create Room Request
```http
POST /api/guest/room-requests
Authorization: Bearer <token>
Content-Type: application/json

{
  "requestType": "HOUSEKEEPING",
  "description": "Need room cleaning and fresh towels",
  "roomNumber": "101"
}
```

**Request Types:**
- HOUSEKEEPING
- MAINTENANCE
- TOWELS
- PILLOWS
- BLANKETS
- EXTRA_BED
- ROOM_SERVICE
- OTHER

### Get My Room Requests
```http
GET /api/guest/room-requests
Authorization: Bearer <token>
```

### Create Pickup/Drop Request
```http
POST /api/guest/pickup-drop
Authorization: Bearer <token>
Content-Type: application/json

{
  "serviceType": "PICKUP",
  "location": "Airport Terminal 1",
  "pickupDateTime": "2024-11-20T10:00:00",
  "numberOfPassengers": 2,
  "flightNumber": "AA123",
  "specialInstructions": "Please arrive 30 minutes early",
  "roomNumber": "101",
  "contactPhone": "+1234567890"
}
```

**Service Types:**
- PICKUP
- DROP
- BOTH

### Get My Pickup/Drop Requests
```http
GET /api/guest/pickup-drop
Authorization: Bearer <token>
```

---

## Admin Endpoints

### Get Hotel Information
```http
GET /api/admin/hotel-info
Authorization: Bearer <admin_token>
```

### Update Hotel Information
```http
PUT /api/admin/hotel-info
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "importantInfo": "Updated important information",
  "checkInTime": "15:00:00",
  "checkOutTime": "12:00:00",
  "breakfastTime": "07:00:00",
  "lunchTime": "12:30:00",
  "dinnerTime": "19:30:00",
  "receptionHours": "24/7",
  "googleMapUrl": "https://maps.google.com/...",
  "wifiPassword": "newpassword2024",
  "wifiNetworkName": "Hotel_Guest_WiFi"
}
```

### Get All Food Orders
```http
GET /api/admin/food-orders
Authorization: Bearer <admin_token>
```

**Query Parameters:**
- `status` (optional): PENDING, CONFIRMED, PREPARING, DELIVERED, CANCELLED

```http
GET /api/admin/food-orders?status=PENDING
Authorization: Bearer <admin_token>
```

### Update Food Order Status
```http
PUT /api/admin/food-orders/{id}/status
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "status": "DELIVERED"
}
```

### Get All Room Requests
```http
GET /api/admin/room-requests
Authorization: Bearer <admin_token>
```

**Query Parameters:**
- `status` (optional): PENDING, IN_PROGRESS, COMPLETED, CANCELLED

### Update Room Request Status
```http
PUT /api/admin/room-requests/{id}/status
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "status": "COMPLETED",
  "adminNotes": "Room cleaned and towels replaced"
}
```

### Get All Pickup/Drop Requests
```http
GET /api/admin/pickup-drop
Authorization: Bearer <admin_token>
```

**Query Parameters:**
- `status` (optional): PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED

### Update Pickup/Drop Request Status
```http
PUT /api/admin/pickup-drop/{id}/status
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "status": "CONFIRMED",
  "adminNotes": "Driver assigned, will arrive at 9:30 AM"
}
```

---

## Status Enums

### Food Order Status
- PENDING
- CONFIRMED
- PREPARING
- DELIVERED
- CANCELLED

### Room Request Status
- PENDING
- IN_PROGRESS
- COMPLETED
- CANCELLED

### Pickup/Drop Request Status
- PENDING
- CONFIRMED
- IN_PROGRESS
- COMPLETED
- CANCELLED

---

## Error Responses

### Validation Error (400 Bad Request)
```json
{
  "fieldName": "Error message"
}
```

### Unauthorized (401)
```json
{
  "error": "Unauthorized"
}
```

### Not Found (404)
```json
{
  "error": "Resource not found"
}
```

---

## Default Admin Credentials

After first run, a default admin user is created:
- **Email:** admin@hotel.com
- **Password:** admin123

**⚠️ Important:** Change the default admin password in production!

