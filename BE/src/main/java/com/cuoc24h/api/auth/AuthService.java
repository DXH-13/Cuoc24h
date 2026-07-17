package com.cuoc24h.api.auth;

import com.cuoc24h.api.auth.dto.LoginRequest;
import com.cuoc24h.api.auth.dto.LoginResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        AdminUser admin = adminUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Sai thong tin dang nhap"));

        if (!admin.isActive() || !passwordEncoder.matches(request.password(), admin.getPasswordHash())) {
            throw new BadCredentialsException("Sai thong tin dang nhap");
        }

        String token = jwtService.generateToken(admin);
        return new LoginResponse(token, admin.getDisplayName(), admin.getRole());
    }
}
