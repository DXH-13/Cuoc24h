package com.cuoc24h.api.driver.dto;

import com.cuoc24h.api.driver.DriverStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDriverStatusRequest(
        @NotNull(message = "Vui long chon trang thai") DriverStatus status,
        @Size(max = 2000, message = "Ghi chu qua dai") String adminNote) {
}
