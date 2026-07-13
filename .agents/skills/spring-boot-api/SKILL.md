---
name: spring-boot-api
description: Spring Boot backend workflow for Cuoc24h. Use when creating or changing Java APIs, services, validation, authentication, booking management, driver management, notification logging, or admin endpoints.
---

# Spring Boot API

## Workflow

1. Read `.agents/rules/10-backend-spring-boot.md`.
2. Check `docs/04-system-architecture.md` and `docs/06-api-spec.md`.
3. Model requests and responses with DTOs.
4. Validate input at the API boundary.
5. Put business behavior in services.
6. Persist data through repositories.
7. Add focused tests for behavior and edge cases.
8. Update API docs if contracts change.

## Booking Rules

- Create booking with status `NEW`.
- Generate a public reference code.
- Save booking before notification.
- Do not fail booking creation because notification fails.
- Record notification status and error details.

## Admin Rules

- Protect admin endpoints.
- Do not expose admin-only notes in public responses.
- Record booking status changes in history.
- Use clear status transitions.

## Validation Focus

- Required customer name and phone.
- Required pickup and dropoff addresses.
- Required pickup time.
- Driver phone and vehicle data.
- Enum/status values.

