
# Online Book System

A RESTful API for managing books, built with **Spring Boot**, **Spring Data JPA**, and **H2 Database** (in-memory for development).  

## Features

- Get all books
- Get a book by ID
- Filter books by:
  - Number of pages (`minPages`, `maxPages`)
  - Rating
  - Title search (partial match)
- Clean API responses using DTOs
- Centralized exception handling
- In-memory H2 database with optional seed data

## Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (development)
- Lombok
- ModelMapper
- Maven

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- IDE (IntelliJ, Eclipse, VS Code)

### Build & Run

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/online-book-system.git
    cd online-book-system
    ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. Access the API at:

   ```
   http://localhost:8080/api/books
   ```

5. Optional: Access H2 console at:

   ```
   http://localhost:8080/h2-console
   ```

   * JDBC URL: `jdbc:h2:mem:bookdb`
   * Username: `sa`
   * Password: *(leave empty)*

## API Endpoints

| Method | Endpoint            | Description                                |
| ------ | ------------------- | ------------------------------------------ |
| GET    | `/api/books`        | Get all books                              |
| GET    | `/api/books/{id}`   | Get book by ID                             |
| GET    | `/api/books/search` | Filter/search books (pages, rating, title) |

**Example query for filtering/search:**

```
GET /api/books/search?minPages=300&maxPages=450&rating=4.5&title=java
```

## Notes / Best Practices

* DTOs are used to prevent exposing entity directly.
* Global exception handling ensures consistent error responses.
* Filtering/search currently happens in memory; can be optimized with **JPA Specifications** for DB-level filtering in production.
* Replace H2 with MySQL/PostgreSQL for production.

## Future Enhancements

* Pagination & sorting
* Create / Update / Delete APIs
* Authentication & Authorization (Spring Security)
* Database migrations using Flyway or Liquibase

```