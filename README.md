# 📈 Spring Boot STOMP WebSocket RabbitMQ H2 Stock Exchange Application

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Overview

A comprehensive **real-time stock exchange application** built with Spring Boot that demonstrates modern microservices patterns and best practices. The application provides live stock updates through WebSockets, message brokering via RabbitMQ, and supports multiple observability stacks for monitoring and tracing.

### Key Highlights
- 🔄 Real-time stock price updates via STOMP WebSocket
- 📊 Dual observability stacks (TICK & Grafana Cloud Stack)
- 🔐 JWT-based authentication with role-based access control
- 📦 Protocol Buffers for efficient serialization
- 🗄️ Dynamic read/write database routing with AOP
- ☁️ Spring Cloud Functions support

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| **Real-time Stock Updates** | STOMP WebSocket protocol for live stock data streaming |
| **Message Brokering** | RabbitMQ integration with STOMP plugin for reliable messaging |
| **In-Memory Database** | H2 database with Flyway migrations for schema management |
| **Dynamic DataSource Routing** | AOP-based read/write separation for optimized database operations |
| **JWT Authentication** | Secure API access with configurable token generation |
| **Role-Based Access Control** | Granular permissions (ADMIN, ADD, FETCH, DELETE) |
| **Scheduled Tasks** | Configurable stock price update scheduler |
| **Cloud Functions** | Spring Cloud Function for serverless stock updates |
| **Protobuf Serialization** | Efficient binary serialization with performance benchmarking |
| **OpenAPI Documentation** | Swagger UI for API exploration |
| **Global Exception Handling** | Centralized error management |
| **Dual Observability Stacks** | TICK stack & Grafana Cloud Stack (GCS) |
| **OpenTelemetry Integration** | Distributed tracing support |
| **Metrics Publishing** | Micrometer + RabbitMQ metrics pipeline |

---

## 🛠️ Tools and Technologies

### Core Stack
| Category | Technology | Version |
|----------|------------|---------|
| **Language** | Java | 21 (with preview features) |
| **Framework** | Spring Boot | 3.5.0 |
| **Build Tool** | Apache Maven | 3.x |
| **Database** | H2 (In-Memory) | Latest |
| **Message Broker** | RabbitMQ | 3.x (with STOMP plugin) |

### Spring Ecosystem
| Component | Purpose |
|-----------|---------|
| Spring WebSocket | STOMP WebSocket support |
| Spring Security | JWT authentication & authorization |
| Spring Data JPA | Database operations |
| Spring AMQP | RabbitMQ integration |
| Spring Cloud Function | Serverless function support |
| Spring Boot Actuator | Health checks & metrics endpoints |

### Libraries & Tools
| Library | Purpose |
|---------|---------|
| **Flyway** | Database migration management |
| **HikariCP** | High-performance connection pooling |
| **MapStruct** | Object mapping (Entity ↔ DTO) |
| **Lombok** | Boilerplate code reduction |
| **JJWT** | JWT token generation & validation |
| **Protocol Buffers** | Binary serialization (v4.28.2) |
| **Micrometer** | Metrics collection (Prometheus/StatsD) |
| **OpenTelemetry** | Distributed tracing |
| **SpringDoc OpenAPI** | API documentation (Swagger UI) |
| **Commons CLI** | Command-line argument parsing |

### Frontend
| Technology | Purpose |
|------------|---------|
| HTML5/CSS3 | UI structure & styling |
| JavaScript | Client-side logic |
| jQuery | DOM manipulation |
| STOMP.js | WebSocket client |
| Bootstrap 3 | UI framework |

---

## 📁 Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/com/cardconnect/stom/stockexchange/
│   │   │   ├── StockexchangeApplication.java     # Main application entry
│   │   │   ├── annotations/                       # Custom annotations
│   │   │   │   ├── ReadOnly.java                 # Read operation marker
│   │   │   │   └── WriteOnly.java                # Write operation marker
│   │   │   ├── aspect/
│   │   │   │   └── DataSourceAspect.java         # AOP for datasource routing
│   │   │   ├── cli/
│   │   │   │   └── JWTCommandLineRunner.java     # CLI for JWT generation
│   │   │   ├── cloud/function/
│   │   │   │   └── StockUpdateSchedulerFunction.java  # Cloud function
│   │   │   ├── config/
│   │   │   │   ├── BrokerProperties.java         # RabbitMQ broker config
│   │   │   │   ├── ProtobufConfig.java           # Protobuf HTTP converter
│   │   │   │   ├── SchedulerConfig.java          # Task scheduler config
│   │   │   │   ├── SchedulerProperties.java      # Scheduler properties
│   │   │   │   ├── SecurityConfig.java           # Spring Security config
│   │   │   │   ├── User.java                     # User roles definition
│   │   │   │   ├── WebSocketConfig.java          # WebSocket STOMP config
│   │   │   │   └── datasource/
│   │   │   │       ├── DataSourceConfig.java     # Datasource beans
│   │   │   │       ├── DataSourceContextHolder.java  # Thread-local context
│   │   │   │       ├── RoutingDataSource.java    # Dynamic routing
│   │   │   │       └── RoutingDataSourceConfig.java  # Routing configuration
│   │   │   ├── controller/
│   │   │   │   └── StockMarketController.java    # REST + WebSocket endpoints
│   │   │   ├── entity/
│   │   │   │   ├── Stock.java                    # JPA entity
│   │   │   │   └── enums/
│   │   │   │       └── DataSourceType.java       # READ/WRITE enum
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java   # Centralized error handling
│   │   │   ├── filter/
│   │   │   │   └── JwtAuthenticationFilter.java  # JWT auth filter
│   │   │   ├── listeners/
│   │   │   │   └── WebSocketEventListener.java   # WebSocket event handling
│   │   │   ├── mapper/
│   │   │   │   └── StockMapper.java              # MapStruct mapper
│   │   │   ├── metrics/
│   │   │   │   ├── MetricsService.java           # Metrics facade
│   │   │   │   └── RabbitMQMetricsPublisher.java # RabbitMQ metrics sender
│   │   │   ├── model/
│   │   │   │   ├── Stock.java                    # Stock DTO
│   │   │   │   └── StockRequest.java             # Request DTO
│   │   │   ├── repository/
│   │   │   │   └── StockRepository.java          # JPA repository
│   │   │   ├── scheduler/
│   │   │   │   └── StockUpdateScheduler.java     # Scheduled tasks
│   │   │   ├── service/
│   │   │   │   └── StockService.java             # Business logic
│   │   │   └── util/
│   │   │       └── JwtUtil.java                  # JWT utilities
│   │   ├── proto/
│   │   │   └── protoBufStock.proto               # Protobuf schema definition
│   │   └── resources/
│   │       ├── application.yml                   # Application configuration
│   │       ├── db/migration/
│   │       │   └── V1__Create_Stock_Table.sql    # Flyway migration
│   │       └── static/
│   │           ├── app.js                        # WebSocket client
│   │           ├── index.html                    # Frontend UI
│   │           └── main.css                      # Styles
│   └── test/
│       └── java/com/cardconnect/stom/stockexchange/
│           └── StockexchangeApplicationTests.java
├── Observability/
│   ├── GCS/                                      # Grafana Cloud Stack
│   │   ├── docker-compose.yml                    # GCS services
│   │   ├── alloy/alloy-local-config.yaml         # Grafana Alloy config
│   │   ├── grafana/datasource.yml                # Grafana datasources
│   │   ├── loki/loki-config.yaml                 # Loki config
│   │   ├── prometheus/prometheus.yml             # Prometheus config
│   │   ├── promtail/promtail-local-config.yaml   # Promtail config
│   │   └── tempo/tempo.yml                       # Tempo config
│   └── TICK/                                     # TICK Stack
│       ├── docker-compose.yml                    # TICK services
│       ├── definitions.json                      # RabbitMQ definitions
│       ├── rabbitmq.conf                         # RabbitMQ config
│       ├── telegraf.conf                         # Telegraf config
│       ├── datasource.yml                        # Grafana datasources
│       └── application.properties                # TICK-specific props
├── pom.xml                                       # Maven build config
└── README.md                                     # This file
```

---

## 🏗️ Architecture

### Component Overview
```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENT LAYER                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│   Browser (index.html + app.js)    │    REST Client / Swagger UI            │
│          ↓ STOMP/WebSocket         │           ↓ HTTP/REST                  │
└─────────────────────────────────────────────────────────────────────────────┘
                                     ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│                           SECURITY LAYER                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│   JwtAuthenticationFilter → SecurityConfig → Role-based Access Control      │
└─────────────────────────────────────────────────────────────────────────────┘
                                     ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│                          APPLICATION LAYER                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│   StockMarketController ← → StockService ← → StockMapper                    │
│          ↓                        ↓                                         │
│   SimpMessagingTemplate    StockUpdateScheduler                             │
│          ↓                        ↓                                         │
│   WebSocket Broker         StockUpdateSchedulerFunction (Cloud)             │
└─────────────────────────────────────────────────────────────────────────────┘
                                     ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│                            DATA LAYER                                       │
├─────────────────────────────────────────────────────────────────────────────┤
│   DataSourceAspect → RoutingDataSource → HikariCP (Reader/Writer Pools)     │
│                              ↓                                              │
│                    StockRepository (JPA)                                    │
│                              ↓                                              │
│                      H2 Database (In-Memory)                                │
└─────────────────────────────────────────────────────────────────────────────┘
                                     ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│                         MESSAGING LAYER                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│   RabbitMQ (STOMP Plugin) ← → MetricsService → RabbitMQMetricsPublisher     │
└─────────────────────────────────────────────────────────────────────────────┘
                                     ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│                       OBSERVABILITY LAYER                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│   TICK Stack                    │    Grafana Cloud Stack (GCS)              │
│   ├── Telegraf (Metrics)        │    ├── Prometheus (Metrics)               │
│   ├── InfluxDB (Storage)        │    ├── Loki (Logs)                        │
│   ├── Chronograf (Viz)          │    ├── Tempo (Traces)                     │
│   ├── Kapacitor (Alerting)      │    ├── Grafana (Visualization)            │
│   └── Grafana (Dashboards)      │    ├── Alloy (Collection Agent)           │
│                                 │    └── MinIO (Object Storage)             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Observability Stacks

### 🔷 TICK Stack (InfluxData)

The TICK stack provides a complete time-series data platform:

| Component | Port | Description |
|-----------|------|-------------|
| **RabbitMQ** | 5672, 15672, 61613 | Message broker with STOMP plugin |
| **Telegraf** | 8125/UDP | Metrics collection agent |
| **InfluxDB** | 8086 | Time-series database |
| **Chronograf** | 8888 | Data visualization & exploration |
| **Kapacitor** | - | Real-time data processing & alerting |
| **Grafana** | 3000 | Dashboards & visualization |

**Start TICK Stack:**
```bash
cd Observability/TICK
docker-compose up -d
```

### 🟢 Grafana Cloud Stack (GCS)

Modern observability stack with distributed architecture:

| Component | Port | Description |
|-----------|------|-------------|
| **Loki** (Read) | 3101 | Log aggregation (read path) |
| **Loki** (Write) | 3102 | Log aggregation (write path) |
| **Loki** (Backend) | 3100 | Log aggregation (backend) |
| **Prometheus** | 9090 | Metrics collection & storage |
| **Tempo** | 3200, 4317, 4318 | Distributed tracing |
| **Grafana** | 3000 | Unified dashboards |
| **Grafana Alloy** | 12345 | Observability data collector |
| **MinIO** | 9000 | S3-compatible object storage |
| **Nginx Gateway** | 3100 | Load balancer for Loki |

**Start GCS Stack:**
```bash
cd Observability/GCS
docker-compose up -d
```

---

## 🔌 API Endpoints

### REST API

| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| `POST` | `/v1/stock` | Add a new stock | ADMIN, ADD |
| `GET` | `/v1/stock` | Get all stocks | ADMIN, FETCH |
| `DELETE` | `/v1/stock/{id}` | Delete a stock | ADMIN, DELETE |
| `GET` | `/v1/stock-buf` | Get stock (Protobuf) | ADMIN, FETCH |

### WebSocket Endpoints

| Type | Endpoint | Description |
|------|----------|-------------|
| STOMP Endpoint | `/stock-exchange` | WebSocket connection endpoint |
| Subscribe | `/topic/stockPrices` | Real-time stock price updates |
| Send | `/app/updateStock` | Send stock update message |

### Cloud Function Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/update-stock-function` | Trigger stock price update function |

### Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/metrics` | Application metrics |
| `/actuator/prometheus` | Prometheus metrics endpoint |

### Documentation

| Endpoint | Description |
|----------|-------------|
| `/swagger-ui/index.html` | Swagger UI |
| `/api-docs` | OpenAPI specification |

---

## ⚙️ Configuration

### Key Application Properties

```yaml
# Server Configuration
server:
  port: 8080

# Database Configuration
spring:
  datasource:
    writer:
      jdbc-url: jdbc:h2:mem:testdb
      pool-name: writer-connection-pool
    reader:
      jdbc-url: jdbc:h2:mem:testdb
      pool-name: reader-connection-pool

# RabbitMQ Configuration
  rabbitmq:
    host: localhost
    port: 5672
    username: user
    password: pass
    virtual-host: local

# JWT Configuration
jwt:
  secret: your-256-bit-secret-your-256-bit-secret
  expiration: 86400000
  issuer: stockexchange

# Scheduler Configuration
scheduler:
  enabled: true
  tasks:
    stock-price-update:
      initial-delay: 5000
      fixed-delay: 30000
      enabled: true

# OpenTelemetry Configuration
otel:
  exporter:
    otlp:
      endpoint: http://localhost:4318
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21+** (with preview features)
- **Maven 3.x**
- **RabbitMQ** (with STOMP plugin enabled)
- **Docker & Docker Compose** (for observability stacks)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd springboot-stomp-websocket-rabbitmq-h2
   ```

2. **Start RabbitMQ with STOMP plugin**
   ```bash
   # Using TICK stack (includes RabbitMQ)
   cd Observability/TICK
   docker-compose up -d rabbitmq
   ```

3. **Build the application**
   ```bash
   ./mvnw clean package -DskipTests
   ```

4. **Run the application**
   ```bash
   java --enable-preview -jar target/stockexchange-0.0.1-SNAPSHOT.jar
   ```

5. **Access the application**
   - Frontend: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - H2 Console: http://localhost:8080/h2-console

### Generate JWT Token

Use the built-in CLI to generate JWT tokens:

```bash
java -jar target/stockexchange-0.0.1-SNAPSHOT.jar generate-token -n <username> -r <role> -i <encoded>
```

**Parameters:**
| Flag | Long | Description |
|------|------|-------------|
| `-n` | `--username` | User name for the token |
| `-r` | `--role` | User role (ADMIN, ADD, FETCH, DELETE) |
| `-i` | `--encoded` | Whether to Base64 encode the token (true/false) |

**Examples:**
```bash
# Generate admin token
java -jar target/stockexchange-0.0.1-SNAPSHOT.jar generate-token -n admin -r ADMIN -i true

# Generate read-only token
java -jar target/stockexchange-0.0.1-SNAPSHOT.jar generate-token -n reader -r FETCH -i true
```

---

## 📦 Protocol Buffers Schema

The application uses Protocol Buffers for efficient serialization:

```protobuf
syntax = "proto3";

package com.cardconnect.stom.stockexchange.proto;

enum StockStatus {
  UNKNOWN = 0;
  ACTIVE = 1;
  INACTIVE = 2;
  DELISTED = 3;
}

message ProtoStockRequest {
  int64 id = 1;
  string name = 2;
  double price = 3;
  StockStatus status = 4;
  repeated string tags = 5;
  map<string, string> attributes = 6;
  StockMetadata metadata = 7;
  oneof stockType {
    string equity = 8;
    string bond = 9;
    string mutualFund = 10;
  }
  repeated StockHistory history = 11;
}

message StockMetadata {
  string createdBy = 1;
  string updatedBy = 2;
  int64 createdAt = 3;
  int64 updatedAt = 4;
}

message StockHistory {
  int64 timestamp = 1;
  double price = 2;
  string note = 3;
}
```

---

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

---

## 🐳 Docker Commands

### Start All Services (TICK)
```bash
cd Observability/TICK
docker-compose up -d
```

### Start All Services (GCS)
```bash
cd Observability/GCS
docker-compose up -d
```

### View Logs
```bash
docker-compose logs -f <service-name>
```

### Stop Services
```bash
docker-compose down
```

### Clean Up (with volumes)
```bash
docker-compose down -v
```

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring WebSocket Guide](https://docs.spring.io/spring-framework/reference/web/websocket.html)
- [RabbitMQ STOMP Plugin](https://www.rabbitmq.com/stomp.html)
- [Protocol Buffers](https://protobuf.dev/)
- [Grafana Documentation](https://grafana.com/docs/)
- [InfluxData TICK Stack](https://www.influxdata.com/time-series-platform/)

---



