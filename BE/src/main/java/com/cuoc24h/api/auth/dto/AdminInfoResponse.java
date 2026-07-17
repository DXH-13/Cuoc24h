package com.cuoc24h.api.auth.dto;

import com.cuoc24h.api.auth.AdminPrincipal;

public record AdminInfoResponse(Long id, String username, String displayName, String role) {

    public static AdminInfoResponse from(AdminPrincipal principal) {
        return new AdminInfoResponse(
                principal.id(), principal.username(), principal.displayName(), principal.role());
    }
}
