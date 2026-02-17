# Comic Store REST API

> Secure RESTful API developed based on the requirements of a real-world technical challenge, focusing on authentication, role-based authorization, layered architecture, and comprehensive testing.

## Overview

This project is a backend REST API for managing a comic book store, inspired by the structure and organization of the Marvel API. It was developed as part of technical interview preparation, emphasizing secure API design, clean architecture, and strong backend fundamentals.

The system handles user authentication, comic management, orders, and coupon generation, with business logic implemented in a structured service layer and protected endpoints using JWT-based security.

---

## Key Highlights

- JWT-based authentication and authorization
- Role-based access control (USER / ADMIN)
- Layered architecture (Controller → Service → Repository)
- RESTful API design with Spring Boot
- DTO pattern for data transfer
- Global exception handling
- Swagger/OpenAPI documentation
- Strong automated test coverage across multiple layers

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA / Hibernate
- MySQL
- H2 Database (for testing)
- Maven
- Swagger / OpenAPI

---

## Architecture

The project follows a layered architecture that separates responsibilities clearly:

- **Controllers** → Handle HTTP requests and responses  
- **Services** → Contain business logic and rules  
- **Repositories** → Manage database access  
- **DTOs** → Handle structured data transfer  
- **Security Layer** → JWT filter and authentication flow  
- **Global Exception Handler** → Centralized error management  

All business rules are implemented in the service layer, preventing direct controller access to repositories and ensuring separation of concerns.

---

## Security

The API implements a secure authentication and authorization system using Spring Security and JWT:

- User registration and login
- JWT token generation
- Token validation via security filter
- Protected endpoints
- Role-based access control:
  - USER
  - ADMIN

All authenticated requests require a valid token.

---

## Testing

This project includes automated tests covering multiple layers of the application:

### Controller Layer
- Tests implemented with **MockMvc**
- Simulates HTTP requests without starting the full application
- Services are mocked to isolate controller behavior

### Service Layer
- **Unit tests using Mockito**
- Repository layer mocked to validate business logic independently

### Repository Layer
- **@DataJpaTest** with H2 in-memory database
- Validates persistence behavior using a real test database context

### Application Context
- **@SpringBootTest** used to verify that the application loads correctly

This multi-layered approach ensures reliability across API behavior, business logic, and data persistence.

---

## Running the Project

### 1) Configure the Database

Edit `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:8080/{database}
spring.datasource.username={username}
spring.datasource.password={password}
```


### 2) Run the Application

```
mvn spring-boot:run
```

### 3) Access Swagger Documentation

```
http://localhost/swagger-ui.html
```

---

## Authentication Flow

To access protected endpoints:

1. Register a new user
2. Login using the same credentials
3. Copy the returned JWT token
4. Use the token:
   - In Swagger via the "Authorize" button
   - Or in the Authorization header using tools like Postman

---

## Main Features

### Authentication
- User registration
- Login with JWT token response
- Secure route protection

### Comic Management

Create comics using:

```
{
"raridade": "[COMUM, RARO]",
"volume": 0,
"titulo": "string",
"editora": "string"
}
```

### Orders:
```
{
"quantidade": 0,
"quadrinhoId": 0
}
```

### Coupons

- Automatic coupon generation
- 10% chance of rare coupon
- Validity between 2 and 12 months
- Custom coupon creation supported

Custom coupon JSON:

```
{
"raridade": "[COMUM, RARO]",
"validade": "year-month-day"
}
```

Validation rules:
- Expiration date cannot be earlier than the current date

---

## Error Handling

The API returns structured error responses for invalid operations.

### Invalid Parameters:

```
InvalidArgumentException
{
errorTime: dd-MM-yyyy HH:mm:ss
message: "Invalid parameter"
httpCode: 400
rejectedValue: value
}
```

### Resource Not Found

```
ResourceNotFoundException
{
errorTime: dd-MM-yyyy HH:mm:ss
message: "Resource not found"
httpCode: 404
id: id
}
```
