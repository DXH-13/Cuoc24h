# Backend Spring Boot Rules

## Architecture

- Use Spring Boot conventions.
- Keep controllers thin.
- Put business logic in services.
- Use DTOs for request and response models.
- Use repositories only for persistence access.
- Centralize exception handling with a global handler.

## API

- Public APIs use `/api`.
- Admin APIs use `/api/admin`.
- Public booking and driver registration endpoints must validate input.
- Admin endpoints must require authentication.
- Keep response shapes consistent with `docs/06-api-spec.md`.

## Data Flow

- Save booking data before sending Zalo notifications.
- Notification failure must not fail booking creation.
- Log notification result as `PENDING`, `SENT`, or `FAILED`.
- Add status history when booking status changes.

## Security

- Hash admin passwords.
- Do not log secrets or full authentication tokens.
- Do not expose internal admin notes through public APIs.
- Avoid returning stack traces to clients.

## Testing

Prefer focused tests for:

- Request validation
- Booking creation
- Notification failure handling
- Admin authorization
- Status transitions

