# Household Errands Service

A Spring Boot REST API that helps households manage their errands, shopping backlogs, and household members.

## Features

- **User Management** — Register and login with email and password. JWT tokens are issued on authentication and are valid for 1 hour.
- **Household Management** — Create and manage households. Each household has a name, a list of members, and a backlog of items.
- **Member Visibility** — Authenticated users can view all members that belong to the same household.
- **Backlog / Item Management** — Add, retrieve, and remove errand items scoped to a specific household.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.2 |
| Security | Spring Security + JWT (JJWT 0.12.6) |
| Persistence | Spring Data JPA + H2 (in-memory) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Logging | SLF4J via Lombok `@Slf4j` |

## Getting Started

### Prerequisites
- Java 21+
- Maven (or use the included `./mvnw` wrapper)

### Run the application

```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Swagger UI

```
http://localhost:8080/household-errands-service/swagger-ui/index.html
```

### H2 Console (in-memory database)

```
http://localhost:8080/household-errands-service/h2-console/
```

- **JDBC URL:** `jdbc:h2:mem:household-db`
- **Username:** `household`
- **Password:** `household`

## API Overview

All endpoints except `/auth/login` and `/auth/register` require a valid JWT Bearer token.

### Authentication — `/auth`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register a new user (optionally link to a household) |
| `POST` | `/auth/login` | Login and receive a JWT token |

### Households — `/household`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/household` | Create a new household |
| `GET` | `/household` | Get all households |
| `GET` | `/household/{householdId}` | Get a household by ID (includes members and items) |
| `PUT` | `/household/{householdId}` | Update a household name |
| `DELETE` | `/household/{householdId}` | Delete a household |
| `GET` | `/household/{householdId}/members` | Get all members of a household |

### Backlog — `/household/{householdId}/backlog`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/household/{householdId}/backlog/item` | Add an item to a household's backlog |
| `GET` | `/household/{householdId}/backlog/list` | Get all items in a household's backlog |
| `GET` | `/household/{householdId}/backlog/item/{itemId}` | Get a specific item |
| `DELETE` | `/household/{householdId}/backlog/item/{itemId}` | Remove an item |

## Seed Data

The application starts with the following seed data (password for all users is `password`):

| Household | User | Email |
|-----------|------|-------|
| Smith Household | John Smith | john.smith@example.com |
| Jones Household | Jane Jones | jane.jones@example.com |

## Project Structure

```
src/main/java/co/za/household/
├── domain/
│   ├── exceptions/       # Custom exceptions (400, 404, 500)
│   └── model/            # API request/response models
├── persistance/
│   ├── model/            # JPA entities (Household, User, Item)
│   └── repository/       # Spring Data repositories
├── service/              # Business logic (Auth, Household, Backlog, JWT)
│   └── impl/
├── translator/           # Entity ↔ domain model mappers
└── web/
    ├── config/           # Security & Swagger configuration
    ├── constants/        # URI constants
    ├── controller/       # REST controllers
    ├── exceptions/       # Global exception handler
    └── security/         # JWT authentication filter
```
