package com.sparta.logistics.infrastructure.feign.dto;

import java.util.UUID;

public record DeliveryStatusResponse(
        UUID deliveryId,
        DeliveryStatus status
) {
}
