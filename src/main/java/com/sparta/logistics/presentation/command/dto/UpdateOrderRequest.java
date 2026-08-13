package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.UpdateOrderCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "주문 수정 요청")
public record UpdateOrderRequest(
        @Schema(description = "주문 수량", example = "5")
        @NotNull(message = "주문 수량은 필수입니다.")
        @Positive(message = "주문 수량은 1 이상이어야 합니다.")
        Integer quantity,

        @Schema(description = "요청사항", example = "배송 전 연락 부탁드립니다.", nullable = true)
        String requestMessage,

        @Schema(description = "납품 기한 일시", example = "2026-08-21T09:00:00Z")
        @NotNull(message = "납품 기한은 필수입니다.")
        Instant dueDate
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
