package com.cuoc24h.api.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AssignDriverRequest(
        @NotNull(message = "Vui long chon tai xe") Long driverId,
        @Size(max = 2000, message = "Ghi chu qua dai") String internalNote) {
}
