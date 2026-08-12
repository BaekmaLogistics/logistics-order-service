package com.sparta.logistics.infrastructure.messaging.event.hub;

import java.time.Instant;
import java.util.UUID;

public record InventoryRestoreFailedPayload(
        UUID orderId,
        UUID hubId,
        UUID productId,
        int quantity,
        String reason,
        Instant occurredAt
) {}