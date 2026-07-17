package com.cuoc24h.api.notification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByBookingIdOrderByCreatedAtDesc(Long bookingId);
}
