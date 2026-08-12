package com.sparta.logistics.infrastructure.messaging.event.delivery;

import java.time.Instant;
import java.util.UUID;

public record DeliveryCanceledPayload(
        UUID orderId,
        UUID deliveryId,
        Instant occurredAt
) {
}