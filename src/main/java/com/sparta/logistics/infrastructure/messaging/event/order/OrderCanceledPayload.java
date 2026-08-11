package com.sparta.logistics.infrastructure.messaging.event.order;

import com.sparta.logistics.domain.entity.Order;

import java.time.Instant;
import java.util.UUID;

public record OrderCanceledPayload(
        UUID orderId,
        UUID ordererUserId,
        UUID deliveryId,
        String canceledReason,
        Instant occurredAt
) {
    public static OrderCanceledPayload from(Order order) {
        return new OrderCanceledPayload(
                order.getId(),
                order.getOrdererUserId(),
                order.getDeliveryId(),
                order.getCanceledReason(),
                order.getCanceledAt()
        );
    }
}
