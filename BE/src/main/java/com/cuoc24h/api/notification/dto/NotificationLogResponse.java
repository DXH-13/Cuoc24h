package com.cuoc24h.api.notification.dto;

import com.cuoc24h.api.notification.NotificationLog;
import com.cuoc24h.api.notification.NotificationStatus;
import java.time.Instant;

public record NotificationLogResponse(
        Long id,
        Long bookingId,
        String channel,
        String recipient,
        String messagePreview,
        NotificationStatus status,
        String errorMessage,
        Instant sentAt,
        Instant createdAt) {

    public static NotificationLogResponse from(NotificationLog n) {
        return new NotificationLogResponse(
                n.getId(),
                n.getBookingId(),
                n.getChannel(),
                n.getRecipient(),
                n.getMessagePreview(),
                n.getStatus(),
                n.getErrorMessage(),
                n.getSentAt(),
                n.getCreatedAt());
    }
}
