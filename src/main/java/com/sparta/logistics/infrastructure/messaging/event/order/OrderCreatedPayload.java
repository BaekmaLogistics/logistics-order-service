package com.sparta.logistics.infrastructure.messaging.event.order;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.domain.entity.Order;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedPayload(
        UUID id,
        UUID departureHubId,
        UUID receiverCompanyId,
        UUID productId,
        int quantity,
        UUID deliveryId,
        String orderStatus,
        String requestMessage,
        Instant dueDate,
        UUID destinationHubId,
        String deliveryAddress,
        String receiverName,
        String receiverSlackId,
        Instant canceledAt,
        String canceledReason,
        Instant occurredAt
) {

    public static OrderCreatedPayload from(Order order, CreateOrderCommand command) {
        return new OrderCreatedPayload(
                order.getId(),
                order.getDepartureHubId(),
                order.getReceiverCompanyId(),
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                order.getStatus().name(),
                order.getRequestMessage(),
                order.getDueDate(),
                command.destinationHubId(),
                command.deliveryAddress(),
                command.receiverName(),
                command.receiverSlackId(),
                order.getCanceledAt(),
                order.getCanceledReason(),
                Instant.now()
        );
    }
}