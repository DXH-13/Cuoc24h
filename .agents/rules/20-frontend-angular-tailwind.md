# Frontend Angular Tailwind Rules

## Architecture

- Use Angular with feature areas for public pages and admin pages.
- Use reactive forms for booking and driver registration.
- Keep reusable UI pieces in shared components once duplication appears.
- Keep API access in services, not components.

## UX

- Design mobile-first.
- Preserve a usable desktop layout.
- Keep booking form short and direct.
- Keep driver registration visually distinct from customer booking.
- Show loading, success, and error states for every submitted form.

## Tailwind

- Prefer utility classes and small reusable components.
- Avoid one-off CSS unless Tailwind cannot express the behavior cleanly.
- Keep forms readable on small screens.
- Make tap targets large enough for mobile use.

## Admin UI

- Desktop admin can use sidebar navigation.
- Mobile admin should use compact navigation and card-style lists.
- Booking status must be visible without opening every detail page.
- Filters should support status and date at minimum.

## Accessibility

- Use real labels for inputs.
- Use semantic buttons and links.
- Do not rely on color alone for status.
- Keep contrast high enough for phone usage outdoors.

