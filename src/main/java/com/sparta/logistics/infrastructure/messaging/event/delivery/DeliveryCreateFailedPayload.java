package com.sparta.logistics.infrastructure.messaging.event.delivery;

import java.time.Instant;
import java.util.UUID;

public record DeliveryCreateFailedPayload(
        UUID orderId,
        String reason,
        Instant occurredAt
) {
}
