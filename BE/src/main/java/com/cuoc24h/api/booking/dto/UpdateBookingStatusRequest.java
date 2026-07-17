package com.cuoc24h.api.booking.dto;

import com.cuoc24h.api.booking.BookingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateBookingStatusRequest(
        @NotNull(message = "Vui long chon trang thai") BookingStatus status,
        @Size(max = 2000, message = "Ghi chu qua dai") String internalNote) {
}
