# User Management System

A comprehensive Spring Boot application for managing users and their settings with CRUD operations, soft delete functionality, Redis caching, and PostgreSQL database integration.

## Features

- **User Management**: Complete CRUD operations for user profiles
- **User Settings**: Manage user preferences (theme, language, notifications)
- **Soft Delete**: Users are marked as deleted rather than permanently removed
- **Redis Caching**: Improved performance with intelligent caching
- **RESTful APIs**: Clean REST endpoints for all operations
- **Docker Support**: Containerized application with docker-compose
- **Layered Architecture**: Proper separation of concerns (Controller → Service → Repository)
- **Input Validation**: Request validation with meaningful error messages
- **Database Integration**: PostgreSQL with Hibernate/JPA

## Technology Stack

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA** - Database operations
- **Spring Data Redis** - Caching layer
- **PostgreSQL** - Primary database
- **Redis** - Cache storage
- **Maven** - Dependency management
- **Docker & Docker Compose** - Containerization

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controller    │────│    Service      │────│   Repository    │
│   (REST APIs)   │    │ (Business Logic)│    │ (Data Access)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │              ┌─────────────────┐              │
         │              │  Redis Cache    │              │
         │              │   (Caching)     │              │
         │              └─────────────────┘              │
         │                                               │
         └───────────────────────────────────────────────┼──────
                                                         │
                                               ┌─────────────────┐
                                               │   PostgreSQL    │
                                               │   (Database)    │
                                               └─────────────────┘
```

## Database Schema

### Users Table
- `id` (Primary Key)
- `first_name`
- `last_name`
- `email` (Unique)
- `dob`
- `phone_country_code`
- `phone_number`
- `profile_pic`
- `email_verified`
- `phone_verified`
- `created_at`
- `updated_at`
- `deleted_at` (Soft Delete)

### User Settings Table
- `id` (Primary Key)
- `user_id` (Foreign Key)
- `notification_enabled`
- `theme`
- `language`
- `created_at`

## API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all active users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Get user by email |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Soft delete user |
| GET | `/api/users/exists/email/{email}` | Check if email exists |

### User Settings

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/user-settings/user/{userId}` | Get user settings |
| PUT | `/api/user-settings` | Update user settings |

## Getting Started

### Prerequisites

- Docker and Docker Compose installed
- Java 17 (if running locally)
- Maven 3.6+ (if running locally)

### Running with Docker (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/rehangodakumbura/user-managment
cd user-management
```

2. Start the application:
```bash
docker-compose up --build
```

3. The application will be available at:
- **API**: http://localhost:8081
- **PostgreSQL**: localhost:5432
- **Redis**: localhost:6379

### Running Locally

1. Start PostgreSQL and Redis:
```bash
docker-compose up postgres redis -d
```

2. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

## Configuration

### Docker Environment
The application uses environment variables in Docker:
- `SPRING_DATASOURCE_URL`: PostgreSQL connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_DATA_REDIS_HOST`: Redis host

### Local Environment
Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_management
    username: postgres
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
```

## API Usage Examples

### Create a User
```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneCountryCode": "+1",
    "phoneNumber": "1234567890"
  }'
```

### Get All Users
```bash
curl http://localhost:8081/api/users
```

### Update User Settings
```bash
curl -X PUT http://localhost:8081/api/user-settings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "theme": "dark",
    "language": "es",
    "notificationEnabled": false
  }'
```

## Key Features Explained

### Soft Delete
Users are never permanently deleted from the database. Instead, they are marked with a `deleted_at` timestamp and excluded from normal queries.

### Redis Caching
- User data is cached with a 10-minute TTL
- Cache is automatically invalidated on updates
- Significantly improves response times for frequently accessed data

### Automatic User Settings
When a new user is created, default settings are automatically generated with:
- `notificationEnabled`: true
- `theme`: "light"
- `language`: "en"

## Development

### Project Structure
```
src/
├── main/java/com/example/usermanagement/
│   ├── UserManagementApplication.java
│   ├── config/
│   │   └── CacheConfig.java
│   ├── controller/
│   │   ├── UserController.java
│   │   └── UserSettingsController.java
│   ├── entity/
│   │   ├── User.java
│   │   └── UserSettings.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── UserSettingsRepository.java
│   └── service/
│       ├── UserService.java
│       └── UserSettingsService.java
└── main/resources/
    └── application.yml
```

### Adding New Features

1. **New Entity**: Create in `entity/` package
2. **Repository**: Extend `JpaRepository` in `repository/` package
3. **Service**: Add business logic in `service/` package
4. **Controller**: Create REST endpoints in `controller/` package

## Monitoring and Debugging

### Docker Commands
```bash
# View logs
docker-compose logs -f app

# Access database
docker exec -it user_management_postgres psql -U postgres -d user_management

# Access Redis
docker exec -it user_management_redis redis-cli

# View cached data
docker exec -it user_management_redis redis-cli keys "*"
```

### Health Checks
- Application runs on port 8081
- Database health can be checked via container logs
- Redis cache status via Redis CLI

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Troubleshooting

### Common Issues

**Port already in use:**
```bash
# Change ports in docker-compose.yml
ports:
  - "8082:8081"  # Use different host port
```

**Database connection failed:**
```bash
# Check if PostgreSQL container is running
docker-compose ps postgres

# Restart services
docker-compose restart
```

**Cache not working:**
```bash
# Verify Redis connection
docker exec -it user_management_redis redis-cli ping
```

## Contact

For questions or support, please open an issue in the GitHub repository.
