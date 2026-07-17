package com.cuoc24h.api.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DriverRepository
        extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

    boolean existsByPhone(String phone);
}
