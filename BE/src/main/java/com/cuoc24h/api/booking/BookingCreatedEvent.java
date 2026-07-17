package com.cuoc24h.api.booking;

/**
 * Published after a booking is persisted. The notification module listens for this
 * AFTER_COMMIT so a Zalo failure can never roll back the booking.
 */
public record BookingCreatedEvent(Long bookingId) {
}
