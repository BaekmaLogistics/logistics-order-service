package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Schema(description = "주문 취소 요청")
public record CancelOrderRequest(
        @Schema(description = "주문 취소 사유", example = "고객 요청으로 인한 취소")
        @NotBlank(message = "주문 취소 사유는 필수입니다.")
        String canceledReason
) {
    public CancelOrderCommand toCommand(UUID orderId) {
        return new CancelOrderCommand(
                orderId,
                canceledReason
        );
    }
}
