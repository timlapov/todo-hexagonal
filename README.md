# Todo App with Hexagonal Architecture

A practice project demonstrating the implementation of **Hexagonal Architecture** (also known as Ports and Adapters) using Java 21 and Spring Boot 3.

## Table of Contents

- [About This Project](#about-this-project)
- [What is Hexagonal Architecture?](#what-is-hexagonal-architecture)
- [Architecture Implementation](#architecture-implementation)
  - [Project Structure](#project-structure)
  - [Dependency Flow](#dependency-flow)
  - [Layer Details](#layer-details)
- [Architecture Diagrams](#architecture-diagrams)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [What I Learned](#what-i-learned)
- [License](#license)

---

## About This Project

This project was created as a **hands-on learning exercise** to understand and practice implementing **Hexagonal Architecture** in a real-world Spring Boot application. The goal was to build a simple Todo management system while strictly adhering to the principles of clean architecture and separation of concerns.

Rather than focusing on feature richness, the emphasis is on:
- **Clean separation of business logic from infrastructure**
- **Framework-independent domain model**
- **Testability and maintainability**
- **Proper dependency management**

---

## What is Hexagonal Architecture?

**Hexagonal Architecture**, introduced by Alistair Cockburn, is an architectural pattern that aims to create loosely coupled application components that can be easily connected to their runtime environment through **ports** and **adapters**.

### Core Principles

1. **Domain at the Center**: Business logic (domain) is the core and should be completely isolated from external concerns (frameworks, databases, UI).

2. **Dependency Inversion**: Dependencies point **inward** toward the domain. External layers depend on inner layers, never the reverse.

3. **Ports**: Interfaces that define how the application communicates with the outside world:
   - **Input Ports** (Driving/Primary): Define use cases that drive the application (e.g., `CreateTaskUseCase`)
   - **Output Ports** (Driven/Secondary): Define contracts for external services the application needs (e.g., `TaskRepository`)

4. **Adapters**: Concrete implementations that connect ports to external systems:
   - **Input Adapters**: REST controllers, CLI, messaging consumers
   - **Output Adapters**: Database repositories, external APIs, file systems

### Benefits

- **Independence from Frameworks**: Domain logic doesn't depend on Spring, JPA, or any framework
- **Testability**: Each layer can be tested in isolation
- **Flexibility**: Easy to swap implementations (e.g., change from SQL to NoSQL database)
- **Maintainability**: Clear boundaries make code easier to understand and modify
- **Business Logic Protection**: Core business rules remain unaffected by infrastructure changes

---

## Architecture Implementation

### Project Structure

This project is organized as a **multi-module Maven project** with clear layer separation:

```
todo-hexagonal/
│
├── domain/                          # CORE DOMAIN LAYER (Pure Java)
│   └── src/main/java/art/lapov/domain/
│       ├── model/                   # Entities & Value Objects
│       │   ├── Task.java
│       │   ├── User.java
│       │   ├── TaskId.java
│       │   ├── UserId.java
│       │   └── TaskStatus.java
│       ├── port/
│       │   ├── in/                  # Input Ports (Use Cases)
│       │   │   ├── CreateTaskUseCase.java
│       │   │   ├── FindTasksUseCase.java
│       │   │   ├── UpdateTaskUserCase.java
│       │   │   ├── DeleteTaskUseCase.java
│       │   │   ├── CreateUserUseCase.java
│       │   │   ├── FindUsersUseCase.java
│       │   │   ├── UpdateUserUseCase.java
│       │   │   └── DeleteUserUseCase.java
│       │   └── out/                 # Output Ports (Repository Interfaces)
│       │       ├── TaskRepository.java
│       │       └── UserRepository.java
│       └── exception/               # Domain Exceptions
│           ├── TaskNotFoundException.java
│           ├── UserNotFoundException.java
│           ├── InvalidEmailException.java
│           └── InvalidInputException.java
│
├── application/                     # APPLICATION LAYER
│   └── src/main/java/art/lapov/application/
│       ├── service/                 # Use Case Implementations
│       │   ├── TaskService.java
│       │   └── UserService.java
│       ├── mapper/                  # Domain ↔ DTO Mappers
│       │   ├── TaskMapper.java
│       │   └── UserMapper.java
│       └── dto/                     # Data Transfer Objects
│           ├── CreateTaskRequest.java
│           ├── UpdateTaskRequest.java
│           ├── TaskResponse.java
│           ├── CreateUserRequest.java
│           ├── UpdateUserRequest.java
│           └── UserResponse.java
│
├── adapters/                        # ADAPTER LAYER
│   ├── adapter-web/                 # REST API Input Adapter
│   │   └── src/main/java/art/lapov/adapterweb/
│   │       ├── TaskController.java
│   │       └── UserController.java
│   │
│   └── adapter-db/                  # Database Output Adapter
│       └── src/main/java/art/lapov/adapterdb/
│           ├── entity/              # JPA Entities
│           │   ├── TaskEntity.java
│           │   └── UserEntity.java
│           ├── repository/
│           │   ├── TaskJpaRepository.java
│           │   ├── UserJpaRepository.java
│           │   ├── TaskRepositoryAdapter.java
│           │   └── UserRepositoryAdapter.java
│           └── mapper/              # Entity ↔ Domain Mappers
│               ├── TaskMapper.java
│               └── UserMapper.java
│
└── bootstrap/                       # BOOTSTRAP & CONFIGURATION
    └── src/main/java/art/lapov/todohexagonal/
        ├── TodoHexagonalApplication.java    # Spring Boot Entry Point
        ├── ApplicationConfiguration.java    # Dependency Injection Wiring
        └── DataInitializer.java            # Sample Data
```

### Dependency Flow

The architecture maintains **strict unidirectional dependency flow** toward the domain:

```
┌─────────────────────────────────────────────────────────────────┐
│                        EXTERNAL WORLD                           │
│                     (HTTP, Database, etc.)                      │
└─────────────────────────────────────────────────────────────────┘
                              ▲
                              │
                    ┌─────────┴─────────┐
                    │                   │
            ┌───────▼────────┐  ┌───────▼───────┐
            │  INPUT ADAPTER │  │ OUTPUT ADAPTER│
            │  (Controllers) │  │ (Repositories)│
            │   adapter-web  │  │  adapter-db   │
            └───────┬────────┘  └──────┬────────┘
                    │                  │
                    │    depends on    │
                    │                  │
            ┌───────▼──────────────────▼─────────┐
            │       APPLICATION LAYER            │
            │       (Use Case Implementations)   │
            │          application/              │
            └───────────────┬────────────────────┘
                            │
                            │    depends on
                            │
                    ┌───────▼────────┐
                    │  DOMAIN LAYER  │
                    │ (Ports, Model) │
                    │    domain/     │
                    └────────────────┘
                            ▲
                            │
                    Pure Java, Zero
                   Framework Dependencies
```

**Key Principle**: Arrows point **INWARD**. The domain has **zero dependencies** on outer layers.

### Layer Details

#### 1. Domain Layer (`domain/`)

**Purpose**: Contains pure business logic and domain model.

**Characteristics**:
- ✅ **Pure Java** - No Spring, JPA, or framework dependencies
- ✅ **Framework-agnostic** - Can be used in any context
- ✅ **Self-contained** - All business rules live here

**Components**:

**Entities**:
- `Task` - Aggregate root representing a task with state machine logic
- `User` - Aggregate root representing a user

**Value Objects**:
- `TaskId` - UUID-based task identifier with validation
- `UserId` - UUID-based user identifier with validation
- `TaskStatus` - Enum (OPEN, IN_PROGRESS, COMPLETED, CANCELLED)

**Input Ports (Use Cases)**:
```java
public interface CreateTaskUseCase {
    Task createTask(String name, String description, UserId userId);
}
```

**Output Ports (Repository Contracts)**:
```java
public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(TaskId id);
    List<Task> findAll();
    void delete(TaskId id);
}
```

#### 2. Application Layer (`application/`)

**Purpose**: Orchestrates use cases by implementing input ports and coordinating domain logic.

**Characteristics**:
- ✅ Depends on **domain** layer only
- ✅ Implements **use case interfaces** from domain
- ✅ Uses **output ports** (repositories) defined in domain
- ✅ Manages transactions
- ✅ Handles validation and error mapping

**Components**:

**Services**:
```java
@Service
public class TaskService implements CreateTaskUseCase, FindTasksUseCase, ... {
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public Task createTask(String name, String description, UserId userId) {
        // Validation
        // Domain object creation
        // Repository interaction
    }
}
```

**Mappers**: Convert between domain models and DTOs

**DTOs**: Request/Response objects for external communication

#### 3. Adapter Layer - Input (`adapter-web/`)

**Purpose**: Exposes REST API endpoints that drive the application.

**Characteristics**:
- ✅ Depends on **domain** (input port interfaces) and **application** (DTOs)
- ✅ Translates HTTP requests to use case calls
- ✅ Maps results to HTTP responses

**Example**:
```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final CreateTaskUseCase createTaskUseCase;
    private final FindTasksUseCase findTasksUseCase;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        Task task = createTaskUseCase.createTask(...);
        return ResponseEntity.ok(taskMapper.toResponse(task));
    }
}
```

**Note**: Controllers depend on **interfaces** (ports), not concrete implementations.

#### 4. Adapter Layer - Output (`adapter-db/`)

**Purpose**: Implements repository ports using Spring Data JPA and H2 database.

**Characteristics**:
- ✅ Depends on **domain** (implements output ports)
- ✅ Translates between domain models and JPA entities
- ✅ Abstracts persistence technology from domain

**Structure**:
```
Domain Model ←→ Adapter Mapper ←→ JPA Entity ←→ H2 Database
```

**Example**:
```java
@Repository
public class TaskRepositoryAdapter implements TaskRepository {
    private final TaskJpaRepository jpaRepository;
    private final TaskMapper mapper;

    @Override
    public Task save(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        TaskEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
```

#### 5. Bootstrap Layer (`bootstrap/`)

**Purpose**: Wires all modules together and bootstraps the Spring application.

**Characteristics**:
- ✅ Depends on **all modules**
- ✅ Configures dependency injection
- ✅ Entry point for the application

**Configuration**:
```java
@Configuration
public class ApplicationConfiguration {
    @Bean
    public TaskService taskService(TaskRepository taskRepository, ...) {
        return new TaskService(taskRepository, ...);
    }

    @Bean
    public TaskRepositoryAdapter taskRepository(TaskJpaRepository jpaRepository, ...) {
        return new TaskRepositoryAdapter(jpaRepository, ...);
    }
}
```

---

## Architecture Diagrams

### Hexagonal Architecture Overview

```
                        ┌──────────────────────────────────────┐
                        │         EXTERNAL WORLD               │
                        │  (Users, HTTP, Database, etc.)       │
                        └──────────────────────────────────────┘
                                        │
                ┌───────────────────────┼───────────────────────┐
                │                       │                       │
                ▼                       │                       ▼
        ┌───────────────┐               │               ┌───────────────┐
        │ INPUT ADAPTER │               │               │OUTPUT ADAPTER │
        │               │               │               │               │
        │   REST API    │               │               │   Database    │
        │ (Controllers) │               │               │ (Repositories)│
        └───────┬───────┘               │               └───────┬───────┘
                │                       │                       │
                │                       │                       │
                ▼                       │                       ▼
        ┌───────────────┐               │               ┌───────────────┐
        │  INPUT PORT   │               │               │  OUTPUT PORT  │
        │  (Use Cases)  │               │               │  (Repository  │
        │  Interfaces   │               │               │   Interface)  │
        └───────┬───────┘               │               └───────▲───────┘
                │                       │                       │
                │       ┌───────────────▼───────────────┐       │
                │       │                               │       │
                └──────▶│        DOMAIN LAYER           │◀──────┘
                        │                               │
                        │  • Entities (Task, User)      │
                        │  • Value Objects (TaskId)     │
                        │  • Business Logic             │
                        │  • Domain Exceptions          │
                        │  • Ports (Interfaces)         │
                        │                               │
                        │  🔒 Zero Framework Deps       │
                        └───────────────────────────────┘
```

### Data Flow: Creating a Task

```
1. HTTP POST Request
   │
   ▼
┌──────────────────────────────────────────────────────────────┐
│  TaskController (Input Adapter - REST)                       │
│  @PostMapping("/api/tasks")                                  │
└──────────────────────────┬───────────────────────────────────┘
                           │
                           │ calls
                           ▼
┌──────────────────────────────────────────────────────────────┐
│  CreateTaskUseCase (Input Port - Interface)                  │
│  Task createTask(String name, String description, UserId)    │
└──────────────────────────┬───────────────────────────────────┘
                           │
                           │ implemented by
                           ▼
┌──────────────────────────────────────────────────────────────┐
│  TaskService (Application Layer)                             │
│  • Validates input                                           │
│  • Creates domain Task entity                                │
│  • Calls TaskRepository.save()                               │
└──────────────────────────┬───────────────────────────────────┘
                           │
                           │ uses
                           ▼
┌──────────────────────────────────────────────────────────────┐
│  Task (Domain Entity)                                        │
│  • Business logic                                            │
│  • State machine (status transitions)                        │
│  • Validation rules                                          │
└──────────────────────────────────────────────────────────────┘
                           │
                           │ saved via
                           ▼
┌──────────────────────────────────────────────────────────────┐
│  TaskRepository (Output Port - Interface)                    │
│  Task save(Task task)                                        │
└──────────────────────────┬───────────────────────────────────┘
                           │
                           │ implemented by
                           ▼
┌──────────────────────────────────────────────────────────────┐
│  TaskRepositoryAdapter (Output Adapter)                      │
│  • Maps Task → TaskEntity                                    │
│  • Calls JPA repository                                      │
│  • Maps TaskEntity → Task                                    │
└──────────────────────────┬───────────────────────────────────┘
                           │
                           │ persists to
                           ▼
┌──────────────────────────────────────────────────────────────┐
│  H2 Database (Infrastructure)                                │
│  Tasks table with UUID primary key                           │
└──────────────────────────────────────────────────────────────┘
```

### Module Dependency Graph

```
┌─────────────────────────────────────────────────────────────┐
│                    BOOTSTRAP MODULE                         │
│  • TodoHexagonalApplication (main)                          │
│  • ApplicationConfiguration (DI)                            │
│  • application.properties                                   │
└─────────────┬───────────────────────────┬───────────────────┘
              │                           │
              ├───────────┬───────────────┼───────────────┐
              │           │               │               │
              ▼           ▼               ▼               ▼
      ┌───────────┐ ┌───────────┐ ┌─────────────┐ ┌─────────────┐
      │  DOMAIN   │ │APPLICATION│ │ ADAPTER-WEB │ │ ADAPTER-DB  │
      │           │ │           │ │             │ │             │
      │ No deps   │ │  depends  │ │  depends    │ │  depends    │
      │           │ │  on       │ │  on         │ │  on         │
      │           │ │  DOMAIN   │ │  DOMAIN +   │ │  DOMAIN     │
      │           │ │           │ │  APPLICATION│ │             │
      └───────────┘ └───────────┘ └─────────────┘ └─────────────┘
```

### Task Entity State Machine

```
                    ┌──────────┐
                    │   OPEN   │ ◀── Initial state when created
                    └─────┬────┘
                          │
                          │ inProgress()
                          ▼
                  ┌───────────────┐
                  │  IN_PROGRESS  │
                  └───────┬───────┘
                          │
                          │ complete()
                          ▼
                    ┌──────────┐
                    │COMPLETED │ ◀── Terminal state
                    └──────────┘


         ┌────────────────┐
         │  CANCELLED     │ ◀── Can transition from OPEN or IN_PROGRESS
         └────────────────┘          via cancel()

Rules:
• Can only move to IN_PROGRESS from OPEN
• Can only complete() from IN_PROGRESS
• Cannot complete already COMPLETED task
• Cannot cancel COMPLETED task
• All transitions enforce via IllegalStateException
```

---

## Features

### User Management
- ✅ Create users with validated email addresses
- ✅ Retrieve all users or find by ID
- ✅ Update user information
- ✅ Delete users

### Task Management
- ✅ Create tasks associated with users
- ✅ Retrieve all tasks, filter by user, or find by ID
- ✅ Update task details (name, description)
- ✅ Update task status with state machine enforcement
- ✅ Delete tasks
- ✅ Task ownership validation

### Business Rules
- 📧 Email format validation using regex pattern
- 🔒 UUID format validation for all IDs
- ⚙️ Task status state machine transitions
- 🚫 Prevent invalid state changes (e.g., completing already completed tasks)
- ✅ Required field validation
- 📝 Automatic timestamp tracking (createdAt, updatedAt)

---

## Technologies Used

| Category | Technology | Version |
|----------|-----------|---------|
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 3.5.6 |
| **Build Tool** | Maven | 4.0.0 |
| **Web** | Spring Web (REST) | 3.5.6 |
| **Persistence** | Spring Data JPA | 3.5.6 |
| **Database** | H2 (in-memory) | Runtime |
| **ORM** | Hibernate | Via Spring Data |
| **Utilities** | Lombok | 1.18.42 |
| **Validation** | Jakarta Validation | Via Spring Boot |

---

## Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+** (or use the included Maven wrapper)
- **Git** (to clone the repository)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/timlapov/todo-hexagonal.git
   cd todo-hexagonal
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

   Or using the Maven wrapper:
   ```bash
   ./mvnw clean install
   ```

### Running the Application

1. **Start the application**:
   ```bash
   mvn spring-boot:run -pl bootstrap
   ```

   Or using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run -pl bootstrap
   ```

2. **Verify it's running**:
   - The application starts on `http://localhost:8080`
   - Check the console for: `Started TodoHexagonalApplication`

3. **Access H2 Console** (optional):
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:tododb`
   - Username: `user`
   - Password: `user`

4. **Test the API**:
   ```bash
   # Get all users (includes 3 sample users)
   curl http://localhost:8080/api/users

   # Get all tasks (includes 3 sample tasks)
   curl http://localhost:8080/api/tasks

   # Create a new task
   curl -X POST http://localhost:8080/api/tasks \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Learn Hexagonal Architecture",
       "description": "Study the principles and implement a demo project",
       "userId": "<user-id-from-previous-call>"
     }'
   ```

### Sample Data

The application automatically initializes with sample data on startup:

**Users**:
- John Doe (john.doe@example.com)
- Jane Smith (jane.smith@example.com)
- Bob Johnson (bob.johnson@example.com)

**Tasks**:
- "Task 1" - OPEN
- "Task 2" - IN_PROGRESS
- "Task 3" - COMPLETED

---

## API Endpoints

### Users API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `POST` | `/api/users` | Create new user |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

**Create User Request**:
```json
{
  "firstName": "Alice",
  "lastName": "Wonder",
  "email": "alice@example.com"
}
```

### Tasks API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `GET` | `/api/tasks/user/{userId}` | Get all tasks for a user |
| `POST` | `/api/tasks` | Create new task |
| `PUT` | `/api/tasks/{id}` | Update task details |
| `PATCH` | `/api/tasks/{id}/status` | Update task status |
| `DELETE` | `/api/tasks/{id}` | Delete task |

**Create Task Request**:
```json
{
  "name": "Complete project documentation",
  "description": "Write comprehensive README with examples",
  "userId": "123e4567-e89b-12d3-a456-426614174000"
}
```

**Update Task Status Request**:
```json
{
  "status": "IN_PROGRESS"
}
```

---

## What I Learned

Building this project taught me valuable lessons about software architecture and design:

### 1. **Separation of Concerns**
- How to truly separate business logic from infrastructure
- The importance of keeping the domain model pure and framework-agnostic
- Why mixing layers leads to tight coupling and maintenance nightmares

### 2. **Dependency Inversion Principle**
- Dependencies should point toward abstractions (interfaces), not concrete implementations
- High-level modules (domain) should not depend on low-level modules (infrastructure)
- The power of "programming to interfaces" in practice

### 3. **Ports and Adapters Pattern**
- How to define clear contracts between layers using ports (interfaces)
- The flexibility gained from being able to swap adapters (e.g., replacing H2 with PostgreSQL requires only changing the adapter, not domain or application logic)
- Understanding the difference between **driving** (input) and **driven** (output) ports

### 4. **Domain-Driven Design Concepts**
- **Entities** vs **Value Objects**: When to use identity-based equality vs value-based equality
- **Aggregates**: Task and User as aggregate roots managing their own lifecycle
- **Ubiquitous Language**: Using domain terminology consistently across all layers
- **Domain Events**: State transitions as business events (task completion, status changes)

### 5. **Multi-Module Maven Projects**
- How to structure a Maven project with multiple modules
- Managing inter-module dependencies
- Proper dependency management and dependency scope

### 6. **Spring Boot Without Framework Lock-in**
- Spring can be used as a configuration tool without contaminating domain logic
- Dependency injection can wire pure Java objects together
- Framework-specific annotations belong in adapters and configuration, never in domain

### 7. **Testing Benefits of Hexagonal Architecture**
- Each layer can be tested in complete isolation
- Domain logic can be unit tested without any framework setup
- Adapters can be mocked or swapped for integration tests
- Use cases can be tested with fake repositories

### 8. **Maintainability and Evolution**
- Changes to the database schema only affect the database adapter
- Changes to REST API only affect controllers
- Business rule changes are localized to the domain
- New adapters can be added without touching existing code (Open/Closed Principle)

### 9. **Value Objects and Validation**
- Encapsulating validation logic in value objects (TaskId, UserId with UUID validation)
- Making invalid states unrepresentable (state machine in Task entity)
- Fail-fast validation at object creation time

### 10. **Real-World Trade-offs**
- Hexagonal architecture adds upfront complexity (more interfaces, more mapping)
- The benefits pay off in larger, long-lived projects
- For simple CRUD apps, this might be over-engineering
- The best architecture balances complexity with project needs

### 11. **Practical Implementation Challenges**
- Mapping between layers (Domain ↔ DTO ↔ Entity) requires discipline
- Keeping domain pure requires constant vigilance against framework leakage
- Transaction management lives in application layer, not domain
- Error handling and status codes belong to adapters, not domain

### 12. **Spring Configuration and Wiring**
- How to use `@Configuration` and `@Bean` to wire dependencies manually
- The difference between component scanning and explicit bean configuration
- Understanding when to let Spring auto-configure vs manual wiring

---

## License

This project is open source and available for educational purposes.

---

**Author**: Timothée LAPOV
**Purpose**: Learning and practicing Hexagonal Architecture
**Year**: 2025