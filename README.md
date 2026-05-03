[![CI](https://github.com/Gorbiel/stock_market_sim/actions/workflows/ci.yml/badge.svg)](https://github.com/Gorbiel/stock_market_sim/actions/workflows/ci.yml)
[![CodeQL](https://github.com/Gorbiel/stock_market_sim/actions/workflows/codeql.yml/badge.svg)](https://github.com/Gorbiel/stock_market_sim/actions/workflows/codeql.yml)
![Java](https://img.shields.io/badge/Java-21-orange)
[![Docker](https://img.shields.io/badge/Docker-ready-blue)](https://www.docker.com/)
[![License](https://img.shields.io/badge/license-GPL--3.0-purple)](LICENSE)

# Stock Market Simulator

This project is a backend service implementing a simplified stock market simulator, created as part of a 2026 backend engineering recruitment task.

---

## Overview

The system simulates a minimal stock market with the following components:

- **Wallets** – entities that hold stocks
- **Bank** – a central entity controlling stock availability
- **Audit log** – records all successful wallet operations

### Key simplifications

- Stock price is fixed at 1
- No wallet balance or funds tracking
- No order book – all operations execute immediately
- Bank is the sole liquidity provider

---

## Running the application

### Requirements

- Docker

---

### Docker (recommended)

```bash
SERVER_PORT=8080 docker compose up --build
````

Application will be available at:

```
http://localhost:8080
```

You can choose any port:

```bash
SERVER_PORT=9090 docker compose up --build
```

---

### Reset database

```bash
docker compose down -v
```

---

## Configuration

The application uses environment variables for configuration.

* No `.env` file is required
* All variables have sensible defaults defined in `compose.yaml`
* Optional configuration can be provided via `.env`

See [`.env.example`](.env.example) for reference.

---

## API Documentation

Swagger UI is available at:

```
http://localhost:XXXX/swagger-ui.html
```

(Replace `XXXX` with the selected port)

The documentation includes:

* all endpoints
* request/response schemas
* error responses

---

## Design

The application follows a feature-oriented structure inspired by Domain-Driven Design.

Each domain area (wallets, bank, audit log) contains:

* controller (API layer)
* service (business logic)
* repository (persistence)
* DTOs and models

### Design goals

* group domain logic by feature
* keep clear separation of concerns
* keep implementation simple and explicit
* ensure testability and readability

---

## Availability and Chaos Handling

### Application availability

The application runs multiple instances behind a reverse proxy:

* Two application instances (`app-1`, `app-2`)
* Nginx load balances requests between them
* Instances are stateless and share the same database

This ensures continued availability when a single instance fails.

---

### Chaos endpoint

`POST /chaos` terminates the instance handling the request.

Behavior:

* request hits one instance via Nginx
* that instance terminates itself
* traffic continues to the remaining instance
* Docker restarts the killed instance automatically

---

### Container restart behavior

* `restart: unless-stopped` ensures automatic recovery
* PostgreSQL includes a health check
* No manual intervention is required after failure

---

### Database availability

* Single PostgreSQL instance
* Data persisted via Docker volume (`postgres_data`)
* No replication or failover

#### Limitation

* Database is a single point of failure

---

### Backup strategy

Automated backups using `pg_dump`:

* hourly: last 24
* daily: last 7
* weekly: last 4

Stored in:

```
./backups/
```

---

### Restore from backup

```bash
docker compose stop app-1 app-2 nginx

docker exec -it stock-sim-postgres \
  psql -U stock_user -d stock_sim \
  -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

cat backups/<file>.sql | docker exec -i stock-sim-postgres \
  psql -U stock_user -d stock_sim

docker compose up -d app-1 app-2 nginx
```

---

### Summary of limitations

* Application is resilient to instance failure
* Database is not highly available
* Backups provide recovery, not redundancy

---

## Tools & Technologies

* Java 21
* Spring Boot (Web, Data JPA, Validation)
* PostgreSQL
* Flyway
* Docker & Docker Compose
* Nginx
* Swagger (springdoc-openapi)
* Lombok
* Maven
* GitHub Actions

---

## Notes

* All endpoints follow the task specification
* Only successful operations are logged
* Application can be started with a single command
* Port is configurable via startup parameter

---

## For Recruiters

This project was developed with attention not only to functionality, but also to development workflow and code quality.

You are encouraged to explore:
- closed issues and pull requests for development history
- commit structure and incremental changes
- GitHub Actions workflows (CI, static analysis)
- GitHub project board for task management
- GitHub Issue Templates
- test coverage across layers (repository, service, API)
- Docker-based setup and runtime configuration

The goal was to simulate a realistic backend development process, including:
- iterative feature delivery
- clear separation of concerns
- maintainable and testable code
- production-aware decisions (availability, backups, error handling)

While the implementation intentionally keeps the domain simple (as per task requirements), care was taken to make the overall solution structured, consistent, and extensible.

---

### Running the application (docker recommended)

```bash
SERVER_PORT=XXXX docker compose up --build
````

Replace `XXXX` with the desired port number (e.g. `8080`). If not specified, it defaults to `8080`.

Application will be available at:

[http://localhost:XXXX](http://localhost:8080)