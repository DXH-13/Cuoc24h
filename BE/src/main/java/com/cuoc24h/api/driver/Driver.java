package com.cuoc24h.api.driver;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "drivers")
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "operating_area", nullable = false, length = 255)
    private String operatingArea;

    @Column(name = "vehicle_type", nullable = false, length = 50)
    private String vehicleType;

    @Column(name = "license_plate", nullable = false, length = 30)
    private String licensePlate;

    @Column(name = "seat_count", nullable = false)
    private Integer seatCount;

    @Column(name = "service_type", nullable = false, length = 80)
    private String serviceType;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    /** Admin-only; never exposed through public APIs. */
    @Column(name = "admin_note", columnDefinition = "TEXT")
    private String adminNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private DriverStatus status = DriverStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
