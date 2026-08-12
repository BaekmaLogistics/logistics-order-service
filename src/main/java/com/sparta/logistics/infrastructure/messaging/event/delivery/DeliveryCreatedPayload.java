package com.sparta.logistics.infrastructure.messaging.event.delivery;

import java.time.Instant;
import java.util.UUID;

public record DeliveryCreatedPayload(
        UUID orderId,
        UUID deliveryId,
        Instant occurredAt
) {
}
