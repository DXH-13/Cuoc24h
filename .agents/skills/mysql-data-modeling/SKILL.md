---
name: mysql-data-modeling
description: MySQL schema and migration workflow for Cuoc24h. Use when designing tables, migrations, indexes, status fields, JPA entities, or data relationships for bookings, drivers, admins, notification logs, and history records.
---

# MySQL Data Modeling

## Workflow

1. Read `.agents/rules/30-database-mysql.md`.
2. Check `docs/05-database-design.md`.
3. Confirm which workflow the schema supports.
4. Add fields only when they support current MVP behavior or a documented near-term need.
5. Add indexes for expected admin filters and lookups.
6. Update database docs when schema changes.

## Modeling Rules

- Use explicit status fields.
- Keep customer-submitted notes separate from admin notes.
- Keep booking notification logs separate from booking records.
- Keep status history separate from current booking status.
- Prefer foreign keys for core relationships.

## Common Queries To Support

- Admin lists bookings by status and created date.
- Admin searches bookings by phone.
- Admin lists drivers by approval status.
- Admin reviews notification failures by booking.

## Review Checklist

- Are timestamps present?
- Are statuses documented?
- Are indexes aligned with admin screens?
- Are sensitive fields protected from public APIs?
- Does the schema preserve booking data if notification fails?

