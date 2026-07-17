package com.cuoc24h.api.booking;

import com.cuoc24h.api.booking.dto.CreateBookingRequest;
import com.cuoc24h.api.booking.dto.CreateBookingResponse;
import com.cuoc24h.api.common.web.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Public booking endpoint. No authentication required (FR-02). */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateBookingResponse>> create(
            @Valid @RequestBody CreateBookingRequest request) {
        CreateBookingResponse result = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(result, "Booking created"));
    }
}
