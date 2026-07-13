# AI Agent Guide For Cuoc24h

This file is the entrypoint for AI coding agents working in this repository.

## Project Context

Cuoc24h is an MVP web platform for independent taxi/service drivers who do not want to join ride-hailing apps. The platform connects customers with drivers, lets customers submit ride requests without registration, lets drivers register, and sends new booking information to a Zalo group for dispatch.

Primary stack:

- Backend: Java Spring Boot
- Database: MySQL
- Frontend: Angular with Tailwind CSS
- Notification: Zalo group notification or a webhook bridge

Read these documents before making product or architecture decisions:

- `docs/00-index.md`
- `docs/01-project-overview.md`
- `docs/02-product-requirements.md`
- `docs/04-system-architecture.md`
- `docs/05-database-design.md`
- `docs/06-api-spec.md`

## Agent Workflow

1. Read the relevant docs and rules before changing code.
2. Keep the MVP scope narrow unless the user explicitly expands it.
3. Prefer small, reviewable changes.
4. Update docs when behavior, APIs, database schema, or workflows change.
5. Run the most relevant checks available in the repo before finishing.
6. Report what changed, what was verified, and any remaining risk.

## Rules

Rules live in `.agents/rules`.

Load these rules by default:

- `.agents/rules/00-project-context.md`
- `.agents/rules/40-security-privacy.md`
- `.agents/rules/50-documentation.md`

Load task-specific rules as needed:

- Backend work: `.agents/rules/10-backend-spring-boot.md`
- Frontend work: `.agents/rules/20-frontend-angular-tailwind.md`
- Database work: `.agents/rules/30-database-mysql.md`

## Skills

Repo-local skills live in `.agents/skills`. They are playbooks for agents; read the relevant `SKILL.md` before starting that type of work.

Use these skills when applicable:

- Product or requirements work: `.agents/skills/cuoc24h-product-planning/SKILL.md`
- Spring Boot API work: `.agents/skills/spring-boot-api/SKILL.md`
- Angular/Tailwind UI work: `.agents/skills/angular-mobile-first-ui/SKILL.md`
- MySQL schema or migration work: `.agents/skills/mysql-data-modeling/SKILL.md`
- Zalo notification work: `.agents/skills/zalo-notification-integration/SKILL.md`

## Product Constraints

- Customers can book rides without creating an account.
- Drivers can register, but admin approval is required before they are active.
- Dispatch is manual in the MVP.
- Booking data must be saved even if Zalo notification fails.
- Admin APIs must require authentication.
- Do not add online payment, realtime tracking, or customer accounts unless requested.

## Code Quality Expectations

- Follow existing project conventions once code exists.
- Keep business logic out of controllers where practical.
- Validate public input on both frontend and backend.
- Never hard-code secrets, tokens, database passwords, or webhook credentials.
- Use clear status values for booking, driver, and notification flows.
- Keep UI mobile-first while preserving desktop usability.

## Communication

Use Vietnamese for user-facing explanations unless the user asks otherwise. Technical names, code, API paths, and commit messages may stay in English.

