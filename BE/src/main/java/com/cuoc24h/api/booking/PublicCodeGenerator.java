package com.cuoc24h.api.booking;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * Generates human-friendly booking references like {@code C24H-20260713-0001}.
 * The daily counter is derived from existing rows so it survives restarts.
 */
@Component
public class PublicCodeGenerator {

    private static final ZoneId ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
    private static final DateTimeFormatter DAY = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String PREFIX = "C24H";

    private final BookingRepository bookingRepository;

    public PublicCodeGenerator(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /** Returns a unique code, retrying on the (rare) collision of a concurrent insert. */
    public String next() {
        String datePart = LocalDate.now(ZONE).format(DAY);
        String dayPrefix = PREFIX + "-" + datePart + "-";
        long todayCount = bookingRepository.countByPublicCodeStartingWith(dayPrefix);

        for (int attempt = 0; attempt < 50; attempt++) {
            String candidate = dayPrefix + String.format("%04d", todayCount + 1 + attempt);
            if (!bookingRepository.existsByPublicCode(candidate)) {
                return candidate;
            }
        }
        // Extremely unlikely fallback.
        return dayPrefix + System.nanoTime();
    }
}
