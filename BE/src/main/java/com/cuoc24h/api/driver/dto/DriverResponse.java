package com.cuoc24h.api.driver.dto;

import com.cuoc24h.api.driver.Driver;
import com.cuoc24h.api.driver.DriverStatus;
import java.time.Instant;

/** Admin view of a driver, including the admin-only note. */
public record DriverResponse(
        Long id,
        String fullName,
        String phone,
        String operatingArea,
        String vehicleType,
        String licensePlate,
        Integer seatCount,
        String serviceType,
        String note,
        String adminNote,
        DriverStatus status,
        Instant createdAt,
        Instant updatedAt) {

    public static DriverResponse from(Driver d) {
        return new DriverResponse(
                d.getId(),
                d.getFullName(),
                d.getPhone(),
                d.getOperatingArea(),
                d.getVehicleType(),
                d.getLicensePlate(),
                d.getSeatCount(),
                d.getServiceType(),
                d.getNote(),
                d.getAdminNote(),
                d.getStatus(),
                d.getCreatedAt(),
                d.getUpdatedAt());
    }
}
