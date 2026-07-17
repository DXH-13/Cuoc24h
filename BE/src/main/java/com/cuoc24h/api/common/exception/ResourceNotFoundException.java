package com.cuoc24h.api.common.exception;

/** Thrown when a requested entity does not exist. Maps to HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String entity, Object id) {
        return new ResourceNotFoundException("Khong tim thay " + entity + " voi id " + id);
    }
}
