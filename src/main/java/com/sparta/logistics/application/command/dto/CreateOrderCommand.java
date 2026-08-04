package com.sparta.logistics.application.command.dto;

import com.sparta.logistics.domain.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record CreateOrderCommand(
        UUID receiverCompanyId,
        UUID productId,
        Integer quantity,
        OrderStatus status,
        String requestMessage,
        Instant dueDate
) {
}
