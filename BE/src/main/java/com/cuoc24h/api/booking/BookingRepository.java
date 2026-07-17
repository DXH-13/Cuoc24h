package com.cuoc24h.api.booking;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookingRepository
        extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findByPublicCode(String publicCode);

    boolean existsByPublicCode(String publicCode);

    long countByPublicCodeStartingWith(String prefix);
}
