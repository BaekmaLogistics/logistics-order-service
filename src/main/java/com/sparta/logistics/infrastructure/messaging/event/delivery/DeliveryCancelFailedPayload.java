package com.sparta.logistics.infrastructure.messaging.event.delivery;

import java.time.Instant;
import java.util.UUID;

public record DeliveryCancelFailedPayload(
        UUID orderId,
        UUID deliveryId,
        String reason,
        Instant occurredAt
) {
}
