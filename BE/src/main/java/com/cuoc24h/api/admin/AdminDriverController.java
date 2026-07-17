package com.cuoc24h.api.admin;

import com.cuoc24h.api.common.web.ApiResponse;
import com.cuoc24h.api.common.web.PageResponse;
import com.cuoc24h.api.driver.DriverService;
import com.cuoc24h.api.driver.DriverStatus;
import com.cuoc24h.api.driver.dto.DriverResponse;
import com.cuoc24h.api.driver.dto.UpdateDriverStatusRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/drivers")
public class AdminDriverController {

    private final DriverService driverService;

    public AdminDriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<DriverResponse>>> list(
            @RequestParam(required = false) DriverStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(Sort.Direction.DESC, "createdAt"));
        var result = PageResponse.from(driverService.list(status, pageable));
        return ResponseEntity.ok(ApiResponse.of(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverResponse>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.of(driverService.getDetail(id)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DriverResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDriverStatusRequest request) {
        var result = driverService.updateStatus(id, request);
        return ResponseEntity.ok(ApiResponse.of(result, "Cap nhat trang thai tai xe thanh cong"));
    }
}
