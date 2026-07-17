package com.cuoc24h.api.booking.dto;

import com.cuoc24h.api.booking.BookingStatus;

/** Public response after creating a booking (no internal fields). */
public record CreateBookingResponse(String publicCode, BookingStatus status) {
}
