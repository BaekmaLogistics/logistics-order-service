package com.sparta.logistics.infrastructure.messaging.event.order;

import com.sparta.logistics.domain.entity.Order;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedPayload(
        UUID orderId,
        UUID ordererUserId,
        UUID receiverCompanyId,
        UUID productId,
        Integer quantity,
        UUID deliveryId,
        Instant occurredAt
) {
    public static OrderCreatedPayload from(Order order) {
        return new OrderCreatedPayload(
                order.getId(),
                order.getOrdererUserId(),
                order.getReceiverCompanyId(),
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                Instant.now()
        );
    }
}
