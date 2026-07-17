package com.cuoc24h.api.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/** Public booking request. Validated per backend rules. */
public record CreateBookingRequest(
        @NotBlank(message = "Vui long nhap ho ten")
        @Size(max = 120, message = "Ho ten toi da 120 ky tu")
        String customerName,

        @NotBlank(message = "Vui long nhap so dien thoai")
        @Pattern(regexp = "^0\\d{8,10}$", message = "So dien thoai khong hop le")
        String customerPhone,

        @NotBlank(message = "Vui long nhap diem don")
        @Size(max = 255, message = "Diem don toi da 255 ky tu")
        String pickupAddress,

        @NotBlank(message = "Vui long nhap diem den")
        @Size(max = 255, message = "Diem den toi da 255 ky tu")
        String dropoffAddress,

        LocalDateTime pickupTime,

        @Size(max = 50, message = "Loai xe khong hop le")
        String vehicleType,

        @Size(max = 2000, message = "Ghi chu qua dai")
        String note) {
}
