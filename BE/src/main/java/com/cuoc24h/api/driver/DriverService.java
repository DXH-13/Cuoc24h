package com.cuoc24h.api.driver;

import com.cuoc24h.api.common.exception.ResourceNotFoundException;
import com.cuoc24h.api.driver.dto.DriverRegistrationRequest;
import com.cuoc24h.api.driver.dto.DriverRegistrationResponse;
import com.cuoc24h.api.driver.dto.DriverResponse;
import com.cuoc24h.api.driver.dto.UpdateDriverStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // ---- Public flow ---------------------------------------------------------

    @Transactional
    public DriverRegistrationResponse register(DriverRegistrationRequest req) {
        Driver driver = new Driver();
        driver.setFullName(req.fullName().trim());
        driver.setPhone(req.phone().trim());
        driver.setOperatingArea(req.operatingArea().trim());
        driver.setVehicleType(req.vehicleType());
        driver.setLicensePlate(req.licensePlate().trim());
        driver.setSeatCount(req.seatCount());
        driver.setServiceType(req.serviceType());
        driver.setNote(req.note());
        driver.setStatus(DriverStatus.PENDING);

        Driver saved = driverRepository.save(driver);
        return new DriverRegistrationResponse(saved.getStatus());
    }

    // ---- Admin flow ----------------------------------------------------------

    @Transactional(readOnly = true)
    public Page<DriverResponse> list(DriverStatus status, Pageable pageable) {
        Specification<Driver> spec = (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
        return driverRepository.findAll(spec, pageable).map(DriverResponse::from);
    }

    @Transactional(readOnly = true)
    public DriverResponse getDetail(Long id) {
        return DriverResponse.from(findOrThrow(id));
    }

    @Transactional
    public DriverResponse updateStatus(Long id, UpdateDriverStatusRequest req) {
        Driver driver = findOrThrow(id);
        driver.setStatus(req.status());
        if (req.adminNote() != null && !req.adminNote().isBlank()) {
            driver.setAdminNote(req.adminNote());
        }
        return DriverResponse.from(driverRepository.save(driver));
    }

    private Driver findOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("tai xe", id));
    }
}
