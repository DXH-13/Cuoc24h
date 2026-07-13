---
name: angular-mobile-first-ui
description: Angular and Tailwind UI workflow for Cuoc24h. Use when creating or changing public booking pages, driver registration, admin dashboard, responsive layouts, forms, validation states, or mobile-first user flows.
---

# Angular Mobile First UI

## Workflow

1. Read `.agents/rules/20-frontend-angular-tailwind.md`.
2. Check `docs/03-user-flows.md` and `docs/07-ui-ux-guidelines.md`.
3. Design mobile layout first, then expand to desktop.
4. Use reactive forms for submitted data.
5. Add loading, success, validation, and failure states.
6. Keep API access in Angular services.
7. Test at mobile and desktop widths.

## Public Pages

- Home page must make booking action obvious.
- Booking form must be short and easy on a phone.
- Driver registration must not look like the customer booking flow.
- Confirmation pages should tell the user what happens next.

## Admin Pages

- Show booking status clearly in lists.
- Support filtering by status and date.
- Keep detail pages actionable: contact info, trip info, internal note, status update.
- Keep mobile admin usable with compact cards or stacked rows.

## UI Quality

- Use labels for every input.
- Avoid text overflow on small screens.
- Keep tap targets comfortable.
- Do not rely only on color to express status.

