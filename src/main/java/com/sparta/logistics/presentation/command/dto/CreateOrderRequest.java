package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID receiverCompanyId,
        @NotNull UUID productId,
        @NotNull @Positive Integer quantity,
        String requestMessage,
        @NotNull Instant dueDate,

        @NotNull(message = "출발 허브 ID는 필수입니다.")
        UUID departureHubId,

        @NotNull(message = "도착 허브 ID는 필수입니다.")
        UUID destinationHubId,

        @NotBlank(message = "배송지 주소는 필수입니다.")
        String deliveryAddress,

        @NotBlank(message = "수령인 이름은 필수입니다.")
        String receiverName,

        @NotBlank(message = "수령인 슬랙 ID는 필수입니다.")
        String receiverSlackId
) {
    public CreateOrderCommand toCommand() {
        return new CreateOrderCommand(
                receiverCompanyId,
                productId,
                quantity,
                requestMessage,
                dueDate,
                departureHubId,
                destinationHubId,
                deliveryAddress,
                receiverName,
                receiverSlackId
        );
    }
}
