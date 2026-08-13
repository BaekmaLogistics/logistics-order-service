package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.ChangeOrderStatusCommand;
import com.sparta.logistics.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "주문 상태 변경 요청")
public record ChangeOrderStatusRequest(
        @Schema(description = "변경할 주문 상태", example = "DELIVERING")
        @NotNull(message = "주문 상태는 필수입니다.")
        OrderStatus status
) {
    public ChangeOrderStatusCommand toCommand(UUID orderId) {
        return new ChangeOrderStatusCommand(orderId, status);
    }
}
