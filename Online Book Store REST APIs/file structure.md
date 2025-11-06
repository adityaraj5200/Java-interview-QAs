online-book-system/
│
├── pom.xml                             # Maven dependencies (Spring Boot, JPA, etc.)
│
├── src/
│   ├── main/
│   │   ├── java/com/aditya/onlinebooksystem/
│   │   │   ├── controller/
│   │   │   │   └── BookController.java          # Handles HTTP requests (GET, filter, search, etc.)
│   │   │   │
│   │   │   ├── service/
│   │   │   │   └── BookService.java             # Business logic layer
│   │   │   │   └── impl/
│   │   │   │       └── BookServiceImpl.java     # Implementation of BookService
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── BookRepository.java          # Extends JpaRepository
│   │   │   │
│   │   │   ├── model/
│   │   │   │   └── Book.java                    # Entity class representing Book table
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   └── BookResponseDto.java         # For response objects (optional, for clean API)
│   │   │   │   └── BookFilterRequestDto.java    # For filter/search request payloads
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java  # Centralized exception handling
│   │   │   │   └── BookNotFoundException.java   # Custom exception
│   │   │   │
│   │   │   ├── mapper/
│   │   │   │   └── BookMapper.java              # Converts between Entity ↔ DTO
│   │   │   │
│   │   │   └── OnlineBookSystemApplication.java # Spring Boot entry point
│   │   │
│   │   └── resources/
│   │       ├── application.yml                  # Environment configs (DB, logging, etc.)
│   │       └── data.sql                         # Optional seed data
│   │
│   └── test/java/com/aditya/onlinebooksystem/
│       ├── controller/BookControllerTest.java   # Unit/integration tests for controller
│       ├── service/BookServiceTest.java         # Unit tests for service
│       └── repository/BookRepositoryTest.java   # Tests for JPA queries
│
└── README.md                                    # Documentation for setup & usage
