package com.cuoc24h.api.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Error envelope. {@code errors} is present only for validation failures.
 * Matches docs/06-api-spec.md.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String message, List<FieldError> errors) {

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message, null);
    }

    public static ErrorResponse of(String message, List<FieldError> errors) {
        return new ErrorResponse(message, errors);
    }

    public record FieldError(String field, String message) {
    }
}
