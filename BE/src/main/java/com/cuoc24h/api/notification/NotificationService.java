package com.cuoc24h.api.notification;

import com.cuoc24h.api.booking.Booking;
import com.cuoc24h.api.booking.BookingCreatedEvent;
import com.cuoc24h.api.booking.BookingRepository;
import com.cuoc24h.api.common.exception.ResourceNotFoundException;
import com.cuoc24h.api.notification.dto.NotificationLogResponse;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Sends the "new booking" message to the Zalo group and records every attempt in
 * {@code notification_logs}. Delivery is triggered AFTER the booking transaction
 * commits, so a Zalo failure can never roll back a booking.
 */
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final NotificationLogRepository logRepository;
    private final BookingRepository bookingRepository;
    private final ZaloWebhookClient webhookClient;
    private final ZaloProperties zaloProperties;

    public NotificationService(
            NotificationLogRepository logRepository,
            BookingRepository bookingRepository,
            ZaloWebhookClient webhookClient,
            ZaloProperties zaloProperties) {
        this.logRepository = logRepository;
        this.bookingRepository = bookingRepository;
        this.webhookClient = webhookClient;
        this.zaloProperties = zaloProperties;
    }

    /**
     * Reacts to a committed booking. Runs in its own transaction (REQUIRES_NEW) and
     * swallows every error so notification problems stay isolated from booking flow.
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onBookingCreated(BookingCreatedEvent event) {
        try {
            Booking booking = bookingRepository.findById(event.bookingId()).orElse(null);
            if (booking == null) {
                log.warn("Booking {} not found when sending notification", event.bookingId());
                return;
            }
            NotificationLog entry = createPendingLog(booking);
            deliver(entry, booking);
        } catch (Exception ex) {
            // Never propagate: booking is already committed.
            log.error("Notification handling failed for booking {}", event.bookingId(), ex);
        }
    }

    @Transactional
    public NotificationLogResponse retry(Long logId) {
        NotificationLog entry = logRepository.findById(logId)
                .orElseThrow(() -> ResourceNotFoundException.of("notification log", logId));
        Booking booking = bookingRepository.findById(entry.getBookingId())
                .orElseThrow(() -> ResourceNotFoundException.of("booking", entry.getBookingId()));
        entry.setMessagePreview(buildMessage(booking));
        entry.setErrorMessage(null);
        entry.setStatus(NotificationStatus.PENDING);
        deliver(entry, booking);
        return NotificationLogResponse.from(entry);
    }

    @Transactional(readOnly = true)
    public List<NotificationLogResponse> getLogsForBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw ResourceNotFoundException.of("booking", bookingId);
        }
        return logRepository.findByBookingIdOrderByCreatedAtDesc(bookingId).stream()
                .map(NotificationLogResponse::from)
                .toList();
    }

    // ---- Internals -----------------------------------------------------------

    private NotificationLog createPendingLog(Booking booking) {
        NotificationLog entry = new NotificationLog();
        entry.setBookingId(booking.getId());
        entry.setChannel("ZALO");
        entry.setRecipient(zaloProperties.recipient());
        entry.setMessagePreview(buildMessage(booking));
        entry.setStatus(NotificationStatus.PENDING);
        return logRepository.save(entry);
    }

    /** Attempts delivery and updates the log to SENT or FAILED. Never throws. */
    private void deliver(NotificationLog entry, Booking booking) {
        try {
            if (!zaloProperties.enabled()) {
                // MVP no-op: record as sent without any outbound call.
                markSent(entry, "Zalo notification disabled (no-op)");
                return;
            }
            if (zaloProperties.webhookUrl() == null || zaloProperties.webhookUrl().isBlank()) {
                markFailed(entry, "ZALO_WEBHOOK_URL chua duoc cau hinh");
                return;
            }
            webhookClient.send(
                    zaloProperties.webhookUrl(),
                    zaloProperties.webhookSecret(),
                    zaloProperties.recipient(),
                    entry.getMessagePreview());
            markSent(entry, null);
        } catch (Exception ex) {
            log.warn("Zalo delivery failed for booking {}: {}", booking.getId(), ex.getMessage());
            markFailed(entry, ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private void markSent(NotificationLog entry, String note) {
        entry.setStatus(NotificationStatus.SENT);
        entry.setSentAt(Instant.now());
        if (note != null) {
            entry.setErrorMessage(note);
        }
        logRepository.save(entry);
    }

    private void markFailed(NotificationLog entry, String error) {
        entry.setStatus(NotificationStatus.FAILED);
        // Store a bounded error string; never log secrets.
        entry.setErrorMessage(error != null && error.length() > 1000 ? error.substring(0, 1000) : error);
        logRepository.save(entry);
    }

    private String buildMessage(Booking b) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cuoc moi: ").append(b.getPublicCode()).append('\n');
        sb.append("Khach: ").append(b.getCustomerName()).append(" - ").append(b.getCustomerPhone()).append('\n');
        sb.append("Don: ").append(b.getPickupAddress()).append('\n');
        sb.append("Den: ").append(b.getDropoffAddress()).append('\n');
        if (b.getPickupTime() != null) {
            sb.append("Thoi gian: ").append(b.getPickupTime().format(TIME_FMT)).append('\n');
        }
        if (b.getVehicleType() != null && !b.getVehicleType().isBlank()) {
            sb.append("Loai xe: ").append(b.getVehicleType()).append('\n');
        }
        if (b.getNote() != null && !b.getNote().isBlank()) {
            sb.append("Ghi chu: ").append(b.getNote());
        }
        return sb.toString().strip();
    }
}
