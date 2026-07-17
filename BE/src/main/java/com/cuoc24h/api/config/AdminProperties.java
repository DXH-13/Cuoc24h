package com.cuoc24h.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.admin")
public record AdminProperties(
        String defaultUsername,
        String defaultPassword,
        String defaultDisplayName) {
}
