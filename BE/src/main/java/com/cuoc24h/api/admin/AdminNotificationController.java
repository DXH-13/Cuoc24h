package com.cuoc24h.api.admin;

import com.cuoc24h.api.common.web.ApiResponse;
import com.cuoc24h.api.notification.NotificationService;
import com.cuoc24h.api.notification.dto.NotificationLogResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/notifications")
public class AdminNotificationController {

    private final NotificationService notificationService;

    public AdminNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<ApiResponse<NotificationLogResponse>> retry(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.of(notificationService.retry(id), "Da gui lai thong bao"));
    }
}
