package com.cuoc24h.api.booking.dto;

import com.cuoc24h.api.booking.Booking;
import com.cuoc24h.api.booking.BookingStatus;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/** Full booking view for admin, including internal note and status history. */
public record BookingDetailResponse(
        Long id,
        String publicCode,
        String customerName,
        String customerPhone,
        String pickupAddress,
        String dropoffAddress,
        LocalDateTime pickupTime,
        String vehicleType,
        String note,
        String internalNote,
        BookingStatus status,
        Long assignedDriverId,
        Instant createdAt,
        Instant updatedAt,
        List<StatusHistoryItem> statusHistory) {

    public record StatusHistoryItem(
            BookingStatus oldStatus,
            BookingStatus newStatus,
            String note,
            Long changedByAdminId,
            Instant createdAt) {
    }

    public static BookingDetailResponse from(Booking b, List<StatusHistoryItem> history) {
        return new BookingDetailResponse(
                b.getId(),
                b.getPublicCode(),
                b.getCustomerName(),
                b.getCustomerPhone(),
                b.getPickupAddress(),
                b.getDropoffAddress(),
                b.getPickupTime(),
                b.getVehicleType(),
                b.getNote(),
                b.getInternalNote(),
                b.getStatus(),
                b.getAssignedDriverId(),
                b.getCreatedAt(),
                b.getUpdatedAt(),
                history);
    }
}
