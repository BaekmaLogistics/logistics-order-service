package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record OrderSearchResponse(
        UUID orderId,
        UUID receiverCompanyId,
        UUID productId,
        Integer quantity,
        UUID deliveryId,
        OrderStatus status,
        Instant dueDate,
        Instant createdAt
) {
    public static OrderSearchResponse from(Order order) {
        return new OrderSearchResponse(
                order.getId(),
                order.getReceiverCompanyId(),
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                order.getStatus(),
                order.getDueDate(),
                order.getCreatedAt()
        );
    }
}
