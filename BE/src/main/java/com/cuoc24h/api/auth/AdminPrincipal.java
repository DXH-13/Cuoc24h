package com.cuoc24h.api.auth;

/** Lightweight authenticated admin, set as the security principal by the JWT filter. */
public record AdminPrincipal(Long id, String username, String displayName, String role) {

    public static AdminPrincipal from(AdminUser admin) {
        return new AdminPrincipal(
                admin.getId(), admin.getUsername(), admin.getDisplayName(), admin.getRole());
    }
}
