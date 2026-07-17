package com.cuoc24h.api.config;

import com.cuoc24h.api.auth.AdminUser;
import com.cuoc24h.api.auth.AdminUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds a default admin account on first run so the system is usable out of the box.
 * The password is hashed; change it after first login (see docs/09-admin-driver-management.md).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    public DataInitializer(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            AdminProperties adminProperties) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }

    @Override
    public void run(String... args) {
        if (adminUserRepository.count() > 0) {
            return;
        }
        AdminUser admin = new AdminUser();
        admin.setUsername(adminProperties.defaultUsername());
        admin.setPasswordHash(passwordEncoder.encode(adminProperties.defaultPassword()));
        admin.setDisplayName(adminProperties.defaultDisplayName());
        admin.setRole("ADMIN");
        admin.setActive(true);
        adminUserRepository.save(admin);
        log.info("Seeded default admin user '{}'", adminProperties.defaultUsername());
    }
}
