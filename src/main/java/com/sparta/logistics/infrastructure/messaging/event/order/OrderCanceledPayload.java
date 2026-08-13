package com.sparta.logistics.infrastructure.messaging.event.order;

import com.sparta.logistics.domain.entity.Order;

import java.time.Instant;
import java.util.UUID;

public record OrderCanceledPayload(
        UUID orderId,
        UUID ordererUserId,
        UUID deliveryId,
        UUID departureHubId,
        UUID productId,
        int quantity,
        String canceledReason,
        Instant occurredAt
) {
    public static OrderCanceledPayload from(Order order) {
        return new OrderCanceledPayload(
                order.getId(),
                order.getOrdererUserId(),
                order.getDeliveryId(),
                order.getDepartureHubId(),
                order.getProductId(),
                order.getQuantity(),
                order.getCanceledReason(),
                order.getCanceledAt()
        );
    }
}
