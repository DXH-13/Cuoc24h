package com.cuoc24h.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Vui long nhap ten dang nhap") String username,
        @NotBlank(message = "Vui long nhap mat khau") String password) {
}
