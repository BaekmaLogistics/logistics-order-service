package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.UpdateOrderCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public record UpdateOrderRequest(
        @NotNull @Positive Integer quantity,
        String requestMessage,
        @NotNull Instant dueDate
) {
    public UpdateOrderCommand toCommand(UUID orderId) {
        return new UpdateOrderCommand(
                orderId,
                quantity,
                requestMessage,
                dueDate
        );
    }
}
