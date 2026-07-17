package com.cuoc24h.api.notification;

import java.time.Duration;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Thin HTTP client that pushes a message to the intermediary Zalo webhook
 * (docs/08-zalo-notification.md, Phuong An 1). Throws on any transport/HTTP error
 * so the caller can log the failure — the caller guarantees booking is unaffected.
 */
@Component
public class ZaloWebhookClient {

    private final RestClient restClient;

    public ZaloWebhookClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(8));
        this.restClient = RestClient.builder().requestFactory(factory).build();
    }

    public void send(String webhookUrl, String webhookSecret, String recipient, String message) {
        var request = restClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON);
        if (webhookSecret != null && !webhookSecret.isBlank()) {
            request = request.header("X-Webhook-Secret", webhookSecret);
        }
        request.body(Map.of("recipient", recipient == null ? "" : recipient, "message", message))
                .retrieve()
                .toBodilessEntity();
    }
}
