package com.sparta.logistics.infrastructure.messaging.event.order;

import com.sparta.logistics.domain.entity.Order;

import java.time.Instant;
import java.util.UUID;

public record OrderCompletedPayload(
        UUID orderId,
        UUID ordererUserId,
        UUID deliveryId,
        Instant occurredAt
) {
    public static OrderCompletedPayload from(Order order) {
        return new OrderCompletedPayload(
                order.getId(),
                order.getOrdererUserId(),
                order.getDeliveryId(),
                Instant.now()
        );
    }
}
