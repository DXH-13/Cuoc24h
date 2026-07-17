package com.cuoc24h.api.booking;

import com.cuoc24h.api.booking.dto.AssignDriverRequest;
import com.cuoc24h.api.booking.dto.BookingDetailResponse;
import com.cuoc24h.api.booking.dto.BookingSummaryResponse;
import com.cuoc24h.api.booking.dto.CreateBookingRequest;
import com.cuoc24h.api.booking.dto.CreateBookingResponse;
import com.cuoc24h.api.booking.dto.UpdateBookingStatusRequest;
import com.cuoc24h.api.common.exception.BusinessException;
import com.cuoc24h.api.common.exception.ResourceNotFoundException;
import com.cuoc24h.api.driver.Driver;
import com.cuoc24h.api.driver.DriverRepository;
import com.cuoc24h.api.driver.DriverStatus;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingStatusHistoryRepository historyRepository;
    private final DriverRepository driverRepository;
    private final PublicCodeGenerator publicCodeGenerator;
    private final ApplicationEventPublisher eventPublisher;

    public BookingService(
            BookingRepository bookingRepository,
            BookingStatusHistoryRepository historyRepository,
            DriverRepository driverRepository,
            PublicCodeGenerator publicCodeGenerator,
            ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.historyRepository = historyRepository;
        this.driverRepository = driverRepository;
        this.publicCodeGenerator = publicCodeGenerator;
        this.eventPublisher = eventPublisher;
    }

    // ---- Public flow ---------------------------------------------------------

    @Transactional
    public CreateBookingResponse createBooking(CreateBookingRequest req) {
        Booking booking = new Booking();
        booking.setPublicCode(publicCodeGenerator.next());
        booking.setCustomerName(req.customerName().trim());
        booking.setCustomerPhone(req.customerPhone().trim());
        booking.setPickupAddress(req.pickupAddress().trim());
        booking.setDropoffAddress(req.dropoffAddress().trim());
        booking.setPickupTime(req.pickupTime());
        booking.setVehicleType(req.vehicleType());
        booking.setNote(req.note());
        booking.setStatus(BookingStatus.NEW);

        Booking saved = bookingRepository.save(booking);
        recordHistory(saved, null, BookingStatus.NEW, "Khach hang tao yeu cau", null);

        // Notification is handled AFTER_COMMIT by the notification module.
        eventPublisher.publishEvent(new BookingCreatedEvent(saved.getId()));

        return new CreateBookingResponse(saved.getPublicCode(), saved.getStatus());
    }

    // ---- Admin flow ----------------------------------------------------------

    @Transactional(readOnly = true)
    public Page<BookingSummaryResponse> list(
            BookingStatus status, LocalDate from, LocalDate to, Pageable pageable) {
        Specification<Booking> spec = buildSpec(status, from, to);
        return bookingRepository.findAll(spec, pageable).map(BookingSummaryResponse::from);
    }

    @Transactional(readOnly = true)
    public BookingDetailResponse getDetail(Long id) {
        Booking booking = findOrThrow(id);
        List<BookingDetailResponse.StatusHistoryItem> history =
                historyRepository.findByBookingIdOrderByCreatedAtDesc(id).stream()
                        .map(h -> new BookingDetailResponse.StatusHistoryItem(
                                h.getOldStatus(),
                                h.getNewStatus(),
                                h.getNote(),
                                h.getChangedByAdminId(),
                                h.getCreatedAt()))
                        .toList();
        return BookingDetailResponse.from(booking, history);
    }

    @Transactional
    public BookingDetailResponse updateStatus(Long id, UpdateBookingStatusRequest req, Long adminId) {
        Booking booking = findOrThrow(id);
        BookingStatus current = booking.getStatus();
        BookingStatus target = req.status();

        if (!current.canTransitionTo(target)) {
            throw new BusinessException(
                    "Khong the chuyen trang thai tu " + current + " sang " + target);
        }

        if (req.internalNote() != null && !req.internalNote().isBlank()) {
            booking.setInternalNote(req.internalNote());
        }

        if (current != target) {
            booking.setStatus(target);
            recordHistory(booking, current, target, req.internalNote(), adminId);
        }
        bookingRepository.save(booking);
        return getDetail(id);
    }

    @Transactional
    public BookingDetailResponse assignDriver(Long id, AssignDriverRequest req, Long adminId) {
        Booking booking = findOrThrow(id);
        Driver driver = driverRepository.findById(req.driverId())
                .orElseThrow(() -> ResourceNotFoundException.of("tai xe", req.driverId()));

        if (driver.getStatus() != DriverStatus.APPROVED) {
            throw new BusinessException("Chi co the gan tai xe da duoc duyet (APPROVED)");
        }

        booking.setAssignedDriverId(driver.getId());
        BookingStatus current = booking.getStatus();
        if (current != BookingStatus.ASSIGNED && current.canTransitionTo(BookingStatus.ASSIGNED)) {
            booking.setStatus(BookingStatus.ASSIGNED);
            recordHistory(booking, current, BookingStatus.ASSIGNED,
                    "Gan tai xe #" + driver.getId()
                            + (req.internalNote() != null ? " - " + req.internalNote() : ""),
                    adminId);
        }
        if (req.internalNote() != null && !req.internalNote().isBlank()) {
            booking.setInternalNote(req.internalNote());
        }
        bookingRepository.save(booking);
        return getDetail(id);
    }

    // ---- Helpers -------------------------------------------------------------

    private Booking findOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("booking", id));
    }

    private void recordHistory(
            Booking booking, BookingStatus oldStatus, BookingStatus newStatus,
            String note, Long adminId) {
        BookingStatusHistory h = new BookingStatusHistory();
        h.setBookingId(booking.getId());
        h.setOldStatus(oldStatus);
        h.setNewStatus(newStatus);
        h.setNote(note);
        h.setChangedByAdminId(adminId);
        historyRepository.save(h);
    }

    private Specification<Booking> buildSpec(BookingStatus status, LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"), from.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
            }
            if (to != null) {
                LocalDateTime endExclusive = to.plusDays(1).atStartOfDay();
                predicates.add(cb.lessThan(
                        root.get("createdAt"), endExclusive.toInstant(java.time.ZoneOffset.UTC)));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
