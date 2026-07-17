package com.cuoc24h.api.common.web;

/**
 * Standard success envelope: {@code { "data": ..., "message": ... }}.
 * Matches docs/06-api-spec.md.
 */
public record ApiResponse<T>(T data, String message) {

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(data, message);
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data, "Success");
    }
}
