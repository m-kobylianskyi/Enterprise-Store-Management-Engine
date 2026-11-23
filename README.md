# Enterprise Store Management Engine

A store management backend (catalog, purchasing, stock, sales, staff, loyalty) driven by a **simulation layer** instead of real tills and suppliers. The simulator plays the role of the outside world (customers, suppliers, time), while the store side is built like a real system and does not know the world is fake.

> **Status:** work in progress. The domain model, database migrations and the first services are done; sales, purchasing, simulation engine and API are on the roadmap.

## Tech stack

- Java 27, Spring Boot 4.1
- Spring Data JPA (Hibernate 7)
- PostgreSQL 18
- Flyway (schema is managed by migrations, Hibernate only validates)
- Spring Security (dependency added, configuration planned)

## Key design decisions

- **Virtual time.** Every business timestamp (orders, stock movements, shifts) comes from `SimClockService`, never from `now()`. This allows pausing, speeding up and reproducible runs.
- **Stock as a journal.** `stock_movement` is the source of truth (signed deltas), `stock` is a cached balance. The sum of a product's movements always equals `stock.quantity`.
- **Price snapshots.** Order items store the unit price at the time of sale, so later price changes do not rewrite history.
- **Optimistic locking** (`@Version`) on `stock` to protect against concurrent updates.
- **Simulation data is separated** from business data (`sim_clock`, `sim_config`, `sim_event`), so the simulator can later be extracted into its own service.
- **Modular monolith first.** Clear boundaries now, microservices later if justified.

## Domain model

| Module | Entities |
|---|---|
| Catalog | `Category`, `Product`, `PriceHistory` |
| Purchasing | `Supplier`, `PurchaseOrder`, `PurchaseOrderItem` |
| Stock | `Stock`, `StockMovement` |
| Sales | `Customer`, `SalesOrder`, `SalesOrderItem`, `Payment` |
| Staff | `Employee`, `Shift`, `Payroll` |
| Loyalty | `LoyaltyTransaction` (points with expiry) |
| Accounts | `UserAccount` |
| Simulation | `SimClock`, `SimConfig`, `SimEvent` |

## Getting started

### Requirements

- JDK 27 (or the version set in `pom.xml`)
- PostgreSQL 18 running locally

### Configuration

Connection settings are read from environment variables with defaults:

| Variable | Default |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/shop_db` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `postgres` |

Create the database first:

```sql
CREATE DATABASE shop_db;
```

### Run

```bash
./mvnw spring-boot:run
```

On startup Flyway applies:

- `V1__init.sql`: schema
- `V2__seed.sql`: initial `sim_clock` row and `sim_config` defaults

## Project structure

```
src/main/java/.../
  models/        JPA entities and enums
  repositories/  Spring Data repositories
  services/      business logic (SimClockService, ConfigService, StockService)
src/main/resources/
  db/migration/  Flyway migrations
```

## Roadmap

- [x] Domain model and Flyway migrations
- [x] Simulation clock and config services
- [x] Stock service (receive, sell, write-off, adjust)
- [ ] Purchasing service (supplier orders and goods receipt)
- [ ] Sales service (orders, payments, loyalty points)
- [ ] Payroll run based on shifts
- [ ] Simulation engine (customer flow, supplier deliveries, events)
- [ ] REST API and Spring Security by role
- [ ] Tests (Testcontainers), Docker Compose, CI
- [ ] Extract the simulator into a separate service (event-driven)

## License

TBD
