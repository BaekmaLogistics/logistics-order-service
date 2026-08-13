package com.sparta.logistics.presentation.command.dto;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "주문 생성 요청")
public record CreateOrderRequest(

        @Schema(description = "수령 업체 ID", example = "11111111-1111-1111-1111-111111111111")
        @NotNull(message = "수령 업체 ID는 필수입니다.")
        UUID receiverCompanyId,

        @Schema(description = "상품 ID", example = "22222222-2222-2222-2222-222222222222")
        @NotNull(message = "상품 ID는 필수입니다.")
        UUID productId,

        @Schema(description = "주문 수량", example = "10")
        @NotNull(message = "주문 수량은 필수입니다.")
        @Positive(message = "주문 수량은 1 이상이어야 합니다.")
        Integer quantity,

        @Schema(description = "요청사항", example = "문 앞에 놓아주세요.", nullable = true)
        String requestMessage,

        @Schema(description = "납품 기한 일시", example = "2026-08-20T09:00:00Z")
        @NotNull(message = "납품 기한은 필수입니다.")
        Instant dueDate,

        @Schema(description = "출발 허브 ID", example = "33333333-3333-3333-3333-333333333333")
        @NotNull(message = "출발 허브 ID는 필수입니다.")
        UUID departureHubId,

        @Schema(description = "도착 허브 ID", example = "44444444-4444-4444-4444-444444444444")
        @NotNull(message = "도착 허브 ID는 필수입니다.")
        UUID destinationHubId,

        @Schema(description = "배송지 주소", example = "서울특별시 강남구 테헤란로 123")
        @NotBlank(message = "배송지 주소는 필수입니다.")
        String deliveryAddress,

        @Schema(description = "수령인 이름", example = "홍길동")
        @NotBlank(message = "수령인 이름은 필수입니다.")
        String receiverName,

        @Schema(description = "수령인 Slack ID", example = "hong-test")
        @NotBlank(message = "수령인 슬랙 ID는 필수입니다.")
        String receiverSlackId
) {
    public CreateOrderCommand toCommand(UUID ordererUserId) {
        return new CreateOrderCommand(
                ordererUserId,
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
