package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.ChangeOrderStatusCommand;
import com.sparta.logistics.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChangeOrderStatusRequest(
        @NotNull(message = "주문 상태는 필수입니다.") OrderStatus status
) {
    public ChangeOrderStatusCommand toCommand(UUID orderId) {
        return new ChangeOrderStatusCommand(orderId, status);
    }
}
