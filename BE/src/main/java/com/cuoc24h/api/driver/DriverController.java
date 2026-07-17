package com.cuoc24h.api.driver;

import com.cuoc24h.api.common.web.ApiResponse;
import com.cuoc24h.api.driver.dto.DriverRegistrationRequest;
import com.cuoc24h.api.driver.dto.DriverRegistrationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Public driver registration endpoint (FR-03). No authentication required. */
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DriverRegistrationResponse>> register(
            @Valid @RequestBody DriverRegistrationRequest request) {
        DriverRegistrationResponse result = driverService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(result, "Driver registration submitted"));
    }
}
