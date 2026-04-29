[![CI](https://github.com/Gorbiel/stock_market_sim/actions/workflows/ci.yml/badge.svg)](https://github.com/Gorbiel/stock_market_sim/actions/workflows/ci.yml)
[![CodeQL](https://github.com/Gorbiel/stock_market_sim/actions/workflows/codeql.yml/badge.svg)](https://github.com/Gorbiel/stock_market_sim/actions/workflows/codeql.yml)
![Java](https://img.shields.io/badge/Java-21-orange)
![Docker](https://img.shields.io/badge/Docker-ready-blue)
[![License](https://img.shields.io/badge/license-GPL--3.0-purple)](LICENSE)

# Stock Market Simulator

This project is a backend service implementing a simplified stock market simulator, created as part of a 2026 backend engineering recruitment task.

## Overview

The system simulates a minimal stock market with the following components:

- **Wallets** – entities that hold stocks
- **Bank** – a central entity controlling stock availability
- **Audit log** – records all successful wallet operations

Key simplifications:
- Stock price is fixed at 1
- No wallet balance or funds tracking
- No order book – all operations execute immediately
- Bank is the sole liquidity provider

## API Documentation

The application exposes a REST API for managing wallets, stocks, and audit logs.

Interactive documentation is available via Swagger UI:

http://localhost:8080/swagger-ui.html

All endpoints follow the specification provided in the recruitment task.

## Running the application

### Requirements

- Docker
- Java 21
- Maven

### Docker (recommended)

```bash
docker compose up --build
```

Application will be available at:

http://localhost:8080

To reset the local database:

```bash
docker compose down -v
```

### Local (without Docker)

```bash
mvn spring-boot:run
```

## Configuration

Environment variables are used for configuration.

See [`.env.example`](.env.example) for available configuration variables.

## Design

The application follows a feature-oriented structure inspired by Domain-Driven Design.

Domain areas (e.g. wallets, stocks, audit log) are organized into separate packages, each containing their own API layer, business logic, and persistence components.

Key goals:
- keep domain concepts grouped together
- separate API, business logic, and persistence concerns
- maintain a clear and extensible structure

## Tools & Technologies

- **Java 21**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL**
- **Flyway**
- **Maven**
- **Docker & Docker Compose**
- **Swagger (springdoc-openapi)**
- **Lombok**
- **GitHub Actions (CI/~~CD~~)**

## Notes

- All endpoints are implemented according to the task specification
- Only successful operations are recorded in the audit log
- The system is designed to be runnable locally with a single command
