package com.cuoc24h.api.admin;

import com.cuoc24h.api.auth.AdminPrincipal;
import com.cuoc24h.api.booking.BookingService;
import com.cuoc24h.api.booking.BookingStatus;
import com.cuoc24h.api.booking.dto.AssignDriverRequest;
import com.cuoc24h.api.booking.dto.BookingDetailResponse;
import com.cuoc24h.api.booking.dto.BookingSummaryResponse;
import com.cuoc24h.api.booking.dto.UpdateBookingStatusRequest;
import com.cuoc24h.api.common.web.ApiResponse;
import com.cuoc24h.api.common.web.PageResponse;
import com.cuoc24h.api.notification.NotificationService;
import com.cuoc24h.api.notification.dto.NotificationLogResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;
    private final NotificationService notificationService;

    public AdminBookingController(
            BookingService bookingService, NotificationService notificationService) {
        this.bookingService = bookingService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookingSummaryResponse>>> list(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(Sort.Direction.DESC, "createdAt"));
        var result = PageResponse.from(bookingService.list(status, from, to, pageable));
        return ResponseEntity.ok(ApiResponse.of(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDetailResponse>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(bookingService.getDetail(id)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<BookingDetailResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request,
            @AuthenticationPrincipal AdminPrincipal principal) {
        var result = bookingService.updateStatus(id, request, principal.id());
        return ResponseEntity.ok(ApiResponse.of(result, "Cap nhat trang thai thanh cong"));
    }

    @PatchMapping("/{id}/assign-driver")
    public ResponseEntity<ApiResponse<BookingDetailResponse>> assignDriver(
            @PathVariable Long id,
            @Valid @RequestBody AssignDriverRequest request,
            @AuthenticationPrincipal AdminPrincipal principal) {
        var result = bookingService.assignDriver(id, request, principal.id());
        return ResponseEntity.ok(ApiResponse.of(result, "Gan tai xe thanh cong"));
    }

    @GetMapping("/{id}/notifications")
    public ResponseEntity<ApiResponse<List<NotificationLogResponse>>> notifications(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(notificationService.getLogsForBooking(id)));
    }
}
