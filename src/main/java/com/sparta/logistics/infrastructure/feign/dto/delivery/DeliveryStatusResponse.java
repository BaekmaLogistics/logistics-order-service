package com.sparta.logistics.infrastructure.feign.dto.delivery;

import java.util.UUID;

public record DeliveryStatusResponse(
        UUID deliveryId,
        DeliveryStatus status
) {
}
