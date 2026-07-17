package com.cuoc24h.api.driver.dto;

import com.cuoc24h.api.driver.DriverStatus;

/** Public response after a driver registers. */
public record DriverRegistrationResponse(DriverStatus status) {
}
