---
name: cuoc24h-product-planning
description: Product planning and requirements workflow for Cuoc24h. Use when refining MVP scope, user flows, acceptance criteria, roadmap, admin/driver/customer behavior, or documentation for the independent taxi service platform.
---

# Cuoc24h Product Planning

## Workflow

1. Read `docs/00-index.md`, `docs/01-project-overview.md`, and `docs/02-product-requirements.md`.
2. Identify which role is affected: customer, driver, or admin.
3. Keep the MVP focused on booking, driver registration, admin management, and Zalo notification.
4. Write requirements as observable behavior with acceptance criteria.
5. Update the relevant docs listed in `.agents/rules/50-documentation.md`.

## Decision Rules

- Prefer manual dispatch for MVP.
- Avoid adding customer accounts unless requested.
- Avoid adding payment, realtime tracking, or automatic matching unless requested.
- Keep customer booking anonymous but collect enough contact information for dispatch.
- Keep driver activation controlled by admin approval.

## Output Checklist

- State the changed scope.
- Note affected user roles.
- Add or update acceptance criteria.
- Call out assumptions and deferred items.

