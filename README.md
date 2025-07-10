# Spring Boot STOMP WebSocket RabbitMQ H2 Stock Exchange Application

## Overview
This project is a stock exchange application built using Spring Boot. It provides real-time stock updates, user authentication, and scheduling functionalities. The application leverages STOMP WebSocket for live communication, RabbitMQ for message brokering, and H2 as an in-memory database. It also supports dynamic data source routing for read and write operations, cloud functions for stock updates, and Protobuf serialization for efficient data handling.

## Features
- **Real-time Stock Updates**: Uses STOMP WebSocket for live stock data updates.
- **Message Brokering**: RabbitMQ integration for handling stock-related messages.
- **Database**: H2 in-memory database for storing stock data.
- **Dynamic Data Source Routing**: Separate read and write databases for optimized performance.
- **Authentication**: JWT-based authentication for secure access.
- **Scheduling**: Scheduled tasks for stock updates using Spring Scheduler.
- **Cloud Functions**: `StockUpdateSchedulerFunction` for cloud-based stock updates.
- **Protobuf Serialization**: Efficient serialization of stock data using Protobuf.
- **Exception Handling**: Global exception management for the application.

## Tools and Technologies
- **Languages**: Java, SQL, JavaScript
- **Frameworks**: Spring Boot
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Message Broker**: RabbitMQ
- **Serialization**: Protobuf
- **WebSocket Protocol**: STOMP
- **Authentication**: JWT
- **Containerization**: Docker (optional for TICK stack)
- **Monitoring**: TICK stack (Telegraf, InfluxDB, Chronograf, Kapacitor)

## Project Structure
### Main Components
- **Annotations**: Custom annotations like `ReadOnly` and `WriteOnly`.
- **Aspect**: `DataSourceAspect` for handling dynamic data source routing.
- **CLI**: `JWTCommandLineRunner` for initializing JWT-related configurations.
- **Cloud Functions**: `StockUpdateSchedulerFunction` for cloud-based stock updates.
- **Configuration**: Includes WebSocket, Security, Scheduler, and DataSource configurations.
- **Controllers**: `StockMarketController` for handling stock-related API requests.
- **Entities**: `Stock` entity and related enums.
- **Exception Handling**: `GlobalExceptionHandler` for managing application exceptions.
- **Filters**: `JwtAuthenticationFilter` for securing endpoints.
- **Listeners**: `WebSocketEventListener` for WebSocket events.
- **Mappers**: `StockMapper` for mapping entities and models.
- **Models**: `Stock` and `StockRequest` for data representation.
- **Repositories**: `StockRepository` for database operations.
- **Schedulers**: `StockUpdateScheduler` for periodic stock updates.
- **Services**: `StockService` for business logic.
- **Utilities**: `JwtUtil` for JWT token management.

### Resources
- **Configuration Files**: `application.yml` for application settings.
- **Database Migration**: SQL scripts for database schema setup.
- **Static Files**: Frontend assets like `app.js`, `index.html`, and `main.css`.

### Test
- Unit tests for application components.

### TICK Stack
- Configuration files for RabbitMQ, Telegraf, and Docker Compose.

## Prerequisites
- Java 17+
- Maven
- RabbitMQ
- Docker (optional for TICK stack)

### generate token:
```java -jar target/stockexchange-0.0.1-SNAPSHOT.jar generate-token -n pjaiswal -r ADMIN -i true```

