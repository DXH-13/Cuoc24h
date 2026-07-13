---
name: zalo-notification-integration
description: Zalo notification workflow for Cuoc24h. Use when implementing or changing notification delivery, webhook bridges, Zalo API integration, notification message templates, retry behavior, logs, or failure handling for new bookings.
---

# Zalo Notification Integration

## Workflow

1. Read `docs/08-zalo-notification.md`.
2. Confirm the selected integration path: direct Zalo API, webhook bridge, or manual fallback.
3. Keep notification delivery outside the critical booking persistence path.
4. Save notification logs with status and error details.
5. Keep tokens and webhook secrets in environment variables.
6. Add tests for success and failure paths where practical.

## Required Behavior

- Booking creation must succeed even when notification fails.
- Notification content must include booking code, customer name, phone, pickup, dropoff, pickup time, vehicle type, and note.
- Failed notifications must be visible to admin or at least logged.
- Retry should reuse saved booking data, not require customer resubmission.

## Message Template

Use a concise dispatch-friendly format:

```text
Cuoc moi: {publicCode}
Khach: {customerName} - {customerPhone}
Don: {pickupAddress}
Den: {dropoffAddress}
Thoi gian: {pickupTime}
Loai xe: {vehicleType}
Ghi chu: {note}
```

## Security

- Do not expose Zalo credentials to Angular.
- Do not log secrets.
- Mask sensitive values in non-local logs when practical.

