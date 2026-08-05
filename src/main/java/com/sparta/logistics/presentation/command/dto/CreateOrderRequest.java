package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID receiverCompanyId,
        @NotNull UUID productId,
        @NotNull @Positive Integer quantity,
        String requestMessage,
        @NotNull Instant dueDate
) {
    public CreateOrderCommand toCommand() {
        return new CreateOrderCommand(
                receiverCompanyId,
                productId,
                quantity,
                requestMessage,
                dueDate
        );
    }
}
