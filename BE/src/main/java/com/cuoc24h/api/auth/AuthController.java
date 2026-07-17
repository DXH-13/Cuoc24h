package com.cuoc24h.api.auth;

import com.cuoc24h.api.auth.dto.AdminInfoResponse;
import com.cuoc24h.api.auth.dto.LoginRequest;
import com.cuoc24h.api.auth.dto.LoginResponse;
import com.cuoc24h.api.common.web.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse result = authService.login(request);
        return ResponseEntity.ok(ApiResponse.of(result, "Login successful"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdminInfoResponse>> me(
            @AuthenticationPrincipal AdminPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.of(AdminInfoResponse.from(principal), "Success"));
    }
}
