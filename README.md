# User Management System

A RESTful API built with Spring Boot for managing users and their settings, featuring Redis caching and PostgreSQL database integration.

## Features

- User CRUD operations (Create, Read, Update, Delete with soft delete)
- User settings management
- Email uniqueness validation
- Redis caching for improved performance
- PostgreSQL database integration
- Data validation with proper error handling
- DTO pattern for clean data transfer

## Tech Stack

- **Backend:** Spring Boot 3.5.5
- **Database:** PostgreSQL 17.6
- **Cache:** Redis
- **Build Tool:** Maven
- **Java Version:** 17

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Data Redis
- Spring Boot Starter Validation
- PostgreSQL Driver
- Spring Boot DevTools

## Project Structure

```
src/main/java/com/example/usermanagement/
├── UserManagementApplication.java
├── config/
│   └── CacheConfig.java
├── controller/
│   ├── UserController.java
│   └── UserSettingsController.java
├── dto/
│   ├── CreateUserDTO.java
│   ├── UpdateUserDTO.java
│   ├── UserResponseDTO.java
│   ├── UpdateUserSettingsDTO.java
│   └── UserSettingsResponseDTO.java
├── entity/
│   ├── User.java
│   └── UserSettings.java
├── mapper/
│   └── UserMapper.java
├── repository/
│   ├── UserRepository.java
│   └── UserSettingsRepository.java
└── service/
    ├── UserService.java
    └── UserSettingsService.java
```

## Setup Instructions

### Prerequisites

- Java 17 or higher
- PostgreSQL installed and running
- Redis installed and running
- Maven

### Database Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE user_management;
```

2. Update database credentials in `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_management
    username: your_username
    password: your_password
```

### Redis Setup

Ensure Redis is running on localhost:6379 (default configuration).

### Running the Application

1. Clone the repository
2. Navigate to project directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Get user by email |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user (soft delete) |
| GET | `/api/users/exists/email/{email}` | Check if email exists |

### User Settings Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/user-settings/user/{userId}` | Get user settings |
| PUT | `/api/user-settings` | Update user settings |

## API Usage Examples

### Create User
```json
POST /api/users
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "dob": "1990-05-15T10:30:00",
  "phoneCountryCode": "+1",
  "phoneNumber": "1234567890"
}
```

### Update User Settings
```json
PUT /api/user-settings
{
  "userId": 1,
  "notificationEnabled": false,
  "theme": "dark",
  "language": "es"
}
```

## Database Schema

### Users Table
- id (Primary Key)
- first_name
- last_name
- email (Unique)
- dob
- phone_country_code
- phone_number
- profile_pic
- email_verified
- phone_verified
- created_at
- updated_at
- deleted_at

### User Settings Table
- id (Primary Key)
- user_id (Foreign Key)
- notification_enabled
- theme
- language
- created_at

## Features

### Caching
- Redis caching implemented for user data
- Automatic cache invalidation on updates
- Configurable TTL (10 minutes default)

### Soft Delete
- Users are not permanently deleted
- Deleted users are filtered out from queries
- `deleted_at` timestamp tracks deletion

### Validation
- Email format validation
- Required field validation
- Unique email constraint

## Configuration

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_management
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

  data:
    redis:
      host: localhost
      port: 6379
      timeout: 60000ms

  cache:
    type: redis
    redis:
      time-to-live: 600000

server:
  port: 8080
```

## Testing

Use Postman or any REST client to test the endpoints. A Postman collection is available for import.

## Error Handling

The API returns appropriate HTTP status codes:
- 200 OK - Success
- 201 Created - Resource created
- 400 Bad Request - Invalid input
- 404 Not Found - Resource not found
- 500 Internal Server Error - Server error

## Development Notes

- Uses DTO pattern for clean separation of concerns
- Implements proper logging with SLF4J
- Follows REST API conventions
- Includes comprehensive validation
- Redis serialization configured for Java 8 DateTime types

## Author

Built with Spring Boot and best practices for enterprise applications.
