# Todo Spring Boot API & Testing

This project is the backend service for the comprehensive Task Management (Todo) application, built with Spring Boot. It provides a secure, robust REST API designed to demonstrate best practices in backend architecture, security, and extensive automated testing.

## Key Features

- **Robust REST API**: Full CRUD operations for Users, Projects, Task Lists, and Tasks.
- **Secure Authentication**: Implementation of Spring Security with JSON Web Tokens (JWT) for stateless authentication and endpoint protection.
- **Data Persistence**: Integration with PostgreSQL using Spring Data JPA.
- **DTO Mapping**: Automated object mapping using MapStruct for clear separation between internal entities and external API contracts.
- **Input Validation**: Request payload validation using Hibernate Validator and Jakarta Validation API.
- **Extensive Testing Suite**: A strong emphasis on automated testing, including unit and integration tests using JUnit 5, Mockito, AssertJ, and Spring Boot Test.
- **Test Coverage**: Integrated JaCoCo plugin to enforce and report code coverage metrics.

## Tech Stack

- **Framework**: Java 17, Spring Boot 3.4
- **Database**: PostgreSQL (Production/Dev), H2 Database (In-memory for testing)
- **Security**: Spring Security, JWT (io.jsonwebtoken)
- **Data Access**: Spring Data JPA, Hibernate
- **Utilities**: MapStruct (DTO mapping)
- **Testing**: JUnit 5, Mockito, AssertJ, Spring Security Test, JaCoCo

## Architecture

The project follows a standard layered architecture, ensuring separation of concerns:
- **Controllers (Presentation Layer)**: Handle incoming HTTP requests, validate input, and return HTTP responses.
- **Services (Business Layer)**: Contain the core business logic and transaction management.
- **Repositories (Data Access Layer)**: Interface with the database using Spring Data JPA.
- **Security Layer**: Custom filters and configuration for intercepting requests and validating JWTs.

## Prerequisites

Before running the application, ensure you have the following installed:
- Java 17 Development Kit (JDK)
- Maven 3.8+
- PostgreSQL database server

## Installation and Setup

### 1. Clone the repository
```bash
git clone https://github.com/ecuellarfernandez/todo-springboot-testing.git
cd todo-springboot-testing
```

### 2. Configure the Database
Create a PostgreSQL database for the application. You will need to set your database credentials in the `src/main/resources/application.properties` (or `application.yml`) file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. JWT Configuration
Ensure you have configured a secure JWT secret key in your application properties:

```properties
jwt.secret=your_super_secret_key_for_jwt_generation_must_be_long
jwt.expiration=86400000
```

### 4. Build and Run
You can compile and start the application using the Maven wrapper:

```bash
# Build the project (compiles MapStruct mappers and runs tests)
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
```
The API will be available at `http://localhost:8080/api`.

## Testing Strategy

This repository is designed with a "testing-first" mindset. It separates testing concerns using appropriate tools for each layer.

### Unit Testing
- **Mockito** is used to mock dependencies (like repositories) to test service logic in isolation.
- **AssertJ** provides fluent assertions for clear and readable test validations.

### Integration Testing
- Uses **H2 Database** an in-memory database to test JPA queries and repository methods without affecting the main PostgreSQL database.
- **@SpringBootTest** and **MockMvc** are utilized to test the entire request lifecycle, including security filters, validation, and JSON serialization.
- **Spring Security Test** (@WithMockUser) is used to simulate authenticated users during endpoint testing.

### Code Coverage
The project uses JaCoCo to track test coverage. You can generate a coverage report by running:

```bash
mvn test
```
The HTML report will be generated in `target/site/jacoco/index.html`.

## Development Notes

- **MapStruct**: If you modify DTOs or Entities, you must run `mvn clean compile` to regenerate the mapper implementations.
- **Flyway/Liquibase**: (If applicable) Database schema changes should be managed through migration scripts.
