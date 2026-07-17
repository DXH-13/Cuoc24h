package com.cuoc24h.api.auth.dto;

public record LoginResponse(String accessToken, String displayName, String role) {
}
