package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CancelOrderRequest(
        @NotBlank String canceledReason
) {
    public CancelOrderCommand toCommand(UUID orderId) {
        return new CancelOrderCommand(
                orderId,
                canceledReason
        );
    }
}
