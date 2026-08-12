package com.sparta.logistics.infrastructure.messaging.event.hub;

import java.time.Instant;
import java.util.UUID;

public record InventoryDeductedPayload(
        UUID orderId,
        UUID hubId,
        UUID productId,
        int quantity,
        Instant occurredAt
) {
}