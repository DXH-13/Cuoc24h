# Database MySQL Rules

## Schema

- Use MySQL-compatible types.
- Include `created_at` and `updated_at` on main tables.
- Use explicit status columns for bookings, drivers, and notifications.
- Keep public customer data separate from admin-only notes.

## Core Tables

Follow `docs/05-database-design.md` unless a newer migration intentionally changes it:

- `bookings`
- `drivers`
- `admin_users`
- `notification_logs`
- `booking_status_history`

## Constraints And Indexes

- Add indexes for common filters: status, created date, phone number.
- Keep foreign keys for relationships where practical.
- Avoid deleting operational records; prefer status changes or soft deletion where needed.

## Migration Discipline

- Migrations must be deterministic.
- Do not edit an already-applied migration unless the project is still pre-release and the user agrees.
- Document schema changes in `docs/05-database-design.md`.

