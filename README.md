# Simple Spring Boot REST API

A sample REST API built with Java and Spring Boot for managing real estate projects and their properties.

This repository serves as a practical reference project for learning, reviewing, and preparing for coding assessments that involve developing REST APIs with Spring Boot. It demonstrates a layered application structure, database relationships, request validation, DTO mapping, exception handling, and basic service-layer unit testing.

> **Project scope:** This is a sample implementation and personal development reference, not a production-ready application. Test coverage is intentionally limited to a small number of representative JUnit tests.

## Features

- Retrieve projects and their associated property counts
- Retrieve all properties
- Filter properties by project ID
- Retrieve individual properties by ID
- Create, update, and delete properties
- Validate incoming request payloads
- Manage property statuses using Java enums
- Map JPA entities to request and response DTOs
- Handle validation and malformed request bodies centrally
- Use a service layer to manage business logic
- Include example unit tests using JUnit 5 and Mockito

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.1 | Application framework |
| Spring Web MVC | REST API endpoints |
| Spring Data JPA | Database access |
| Hibernate | Object-relational mapping |
| PostgreSQL | Relational database |
| Jakarta Bean Validation | Request validation |
| Lombok | Boilerplate code reduction |
| Maven | Dependency management and build |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies in unit tests |

## Prerequisites

Make sure you have the following installed:

- JDK 21
- PostgreSQL
- Git
- A Java IDE or code editor, such as Eclipse

The project includes the Maven Wrapper, so a separate Maven installation is optional.

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/zaphiere/simple-springboot-rest-api.git
cd simple-springboot-rest-api
```

### 2. Create the local environment file

The repository includes an `.env.local` file as an example configuration. Copy it to `.env`, which is the file imported by Spring Boot.

**Windows — Command Prompt:**

```cmd
copy .env.local .env
```

**Windows PowerShell, Linux, or macOS:**

```bash
cp .env.local .env
```

Open `.env` and replace the placeholder values with your local PostgreSQL credentials.

```dotenv
DB_URL=jdbc:postgresql://localhost:5432/simplerestapi
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password
```

Keep `.env` local and do not commit actual credentials to the repository. The `.env.local` file serves as the example configuration.

### 3. Create the PostgreSQL database

Create a database named `simplerestapi` in PostgreSQL, or update `DB_URL` in your `.env` file if you prefer a different database name.

```sql
CREATE DATABASE simplerestapi;
```

Ensure that the PostgreSQL user specified in `.env` has the necessary permissions to connect to the database and perform the required operations.

### 4. Run the application

From the project root, execute the appropriate Maven Wrapper command.

**Windows:**

```cmd
mvnw.cmd spring-boot:run
```

**Linux or macOS:**

```bash
./mvnw spring-boot:run
```

By default, the application runs at:

```text
http://localhost:8080
```

Spring Boot uses the configuration in `src/main/resources/application.properties`, which imports the root `.env` file.

The project currently uses Hibernate's `ddl-auto=update` setting to update the database schema during development.

### 5. Run the tests

**Windows:**

```cmd
mvnw.cmd test
```

**Linux or macOS:**

```bash
./mvnw test
```

## API Reference

All endpoints use the `/api` base path.

### Projects

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/projects` | Retrieve all projects, including property counts |

Example request:

```http
GET /api/projects
```

Example response:

```json
[
  {
    "id": 1,
    "name": "Sunset Residences",
    "code": "SUNSET",
    "propertyCount": 10
  }
]
```

The response values are illustrative examples.

### Properties

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/properties` | Retrieve all properties |
| GET | `/api/properties?project_id={id}` | Retrieve properties belonging to a project |
| GET | `/api/properties/{id}` | Retrieve a property by ID |
| POST | `/api/properties` | Create a property |
| PUT | `/api/properties/{id}` | Update an existing property |
| DELETE | `/api/properties/{id}` | Delete a property |

### Retrieve all properties

```http
GET /api/properties
```

### Filter properties by project

```http
GET /api/properties?project_id=1
```

### Retrieve a property by ID

```http
GET /api/properties/1
```

### Create a property

```http
POST /api/properties
Content-Type: application/json
```

Request body:

```json
{
  "projectId": 1,
  "label": "Unit A1",
  "status": "AVAILABLE",
  "price": 100000.00
}
```

A successful request returns `201 Created` with the created property's response data.

### Update a property

```http
PUT /api/properties/1
Content-Type: application/json
```

Request body:

```json
{
  "projectId": 1,
  "label": "Unit A2",
  "status": "RESERVED",
  "price": 120000.00
}
```

A successful request returns `200 OK` with the updated property's response data.

### Delete a property

```http
DELETE /api/properties/1
```

A successful deletion returns `200 OK` with the following response:

```text
Property Deleted
```

## Property Data and Validation

The property request DTO uses Jakarta Bean Validation to validate incoming requests.

| Field | Type | Validation |
|---|---|---|
| `projectId` | Integer | Required, greater than zero, and must reference an existing project |
| `label` | String | Required, maximum of 15 characters |
| `status` | Enum | Required and must be a supported property status |
| `price` | Decimal | Required and greater than zero |

### Supported property statuses

| Status | Description |
|---|---|
| `AVAILABLE` | Property is available |
| `RESERVED` | Property is reserved |
| `SOLD` | Property has been sold |

Status values should be supplied in uppercase.

## Error Handling

The application uses centralized exception handling through Spring's `@RestControllerAdvice`.

| HTTP Status | Meaning |
|---|---|
| `200 OK` | Request completed successfully |
| `201 Created` | Property created successfully |
| `400 Bad Request` | Referenced project does not exist under the current service implementation |
| `404 Not Found` | Requested property does not exist |
| `422 Unprocessable Entity` | Validation or malformed request-body error |

Validation errors include field-specific messages where applicable. Malformed request bodies, including invalid enum values, are handled separately.

The exact response structure depends on the exception handler and the type of error encountered.

## Project Structure

The application follows a layered architecture.

```text
src/
├── main/
│   ├── java/
│   │   └── com/simplespringbootrestapi/app/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── enums/
│   │       ├── exception/
│   │       ├── mapper/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/simplespringbootrestapi/app/
            └── service/
```

### Package responsibilities

- **Controller:** Handles HTTP requests and responses.
- **DTO:** Defines API request and response structures.
- **Enums:** Defines supported property statuses.
- **Exception:** Handles validation and request-body errors.
- **Mapper:** Converts entities into response DTOs.
- **Model:** Defines JPA entities and their relationships.
- **Repository:** Provides database access through Spring Data JPA.
- **Service:** Contains business logic and coordinates persistence.
- **Test:** Contains automated tests for selected service-layer scenarios.

## Testing

The project includes a limited example implementation of unit testing with JUnit 5 and Mockito.

The current tests demonstrate representative service-layer scenarios, including:

- Successfully creating a property when its referenced project exists.
- Throwing an exception when the referenced project does not exist.
- Verifying that no property is saved when the referenced project is missing.

**Test coverage is intentionally incomplete.** The remaining service methods, controllers, validation scenarios, and database interactions have not all been covered by automated tests.

This is deliberate: the repository is primarily a reusable learning and coding-assessment reference, rather than a fully tested application.

## Potential Improvements

Possible
