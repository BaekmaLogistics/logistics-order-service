package com.sparta.logistics.application.command.dto;

import com.sparta.logistics.domain.model.OrderStatus;

import java.util.UUID;

public record ChangeOrderStatusCommand(
        UUID orderId,
        OrderStatus status
) {
}
