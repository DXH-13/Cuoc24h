package com.cuoc24h.api.common.web;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Lightweight, serialization-friendly page envelope so we don't leak Spring's
 * {@code PageImpl} shape (which changes across versions) to clients.
 */
public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages) {

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
