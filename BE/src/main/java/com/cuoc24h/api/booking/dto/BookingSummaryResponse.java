package com.cuoc24h.api.booking.dto;

import com.cuoc24h.api.booking.Booking;
import com.cuoc24h.api.booking.BookingStatus;
import java.time.Instant;
import java.time.LocalDateTime;

/** Compact booking row for admin list views. */
public record BookingSummaryResponse(
        Long id,
        String publicCode,
        String customerName,
        String customerPhone,
        String pickupAddress,
        String dropoffAddress,
        LocalDateTime pickupTime,
        String vehicleType,
        BookingStatus status,
        Long assignedDriverId,
        Instant createdAt) {

    public static BookingSummaryResponse from(Booking b) {
        return new BookingSummaryResponse(
                b.getId(),
                b.getPublicCode(),
                b.getCustomerName(),
                b.getCustomerPhone(),
                b.getPickupAddress(),
                b.getDropoffAddress(),
                b.getPickupTime(),
                b.getVehicleType(),
                b.getStatus(),
                b.getAssignedDriverId(),
                b.getCreatedAt());
    }
}
