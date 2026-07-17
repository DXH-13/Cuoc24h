package com.cuoc24h.api.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zalo notification config, bound from {@code app.zalo.*}. Secrets come from the
 * environment and are never logged (see backend/security rules).
 */
@ConfigurationProperties(prefix = "app.zalo")
public record ZaloProperties(
        boolean enabled,
        String webhookUrl,
        String webhookSecret,
        String recipient) {
}
