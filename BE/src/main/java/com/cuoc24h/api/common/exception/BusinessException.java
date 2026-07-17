package com.cuoc24h.api.common.exception;

import org.springframework.http.HttpStatus;

/** Domain rule violation (e.g. invalid status transition). Maps to a configurable 4xx. */
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
