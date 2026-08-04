package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record OrderDetailResponse(
        UUID orderId,
        UUID receiverCompanyId,
        UUID productId,
        Integer quantity,
        UUID deliveryId,
        OrderStatus status,
        String requestMessage,
        Instant dueDate,
        Instant canceledAt,
        String canceledReason,
        Instant createdAt,
        Instant updatedAt
) {
    public static OrderDetailResponse from(Order order) {
        return new OrderDetailResponse(
                order.getId(),
                order.getReceiverCompanyId(),
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                order.getStatus(),
                order.getRequestMessage(),
                order.getDueDate(),
                order.getCanceledAt(),
                order.getCanceledReason(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
