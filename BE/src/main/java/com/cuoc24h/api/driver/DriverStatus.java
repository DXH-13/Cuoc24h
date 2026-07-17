package com.cuoc24h.api.driver;

/** Lifecycle of a driver profile. See docs/05-database-design.md. */
public enum DriverStatus {
    PENDING,
    APPROVED,
    REJECTED,
    INACTIVE
}
