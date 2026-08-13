package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "주문 상세 응답")
public record OrderDetailResponse(
        @Schema(description = "주문 ID", example = "55555555-5555-5555-5555-555555555555")
        UUID orderId,

        @Schema(description = "수령 업체 ID", example = "11111111-1111-1111-1111-111111111111")
        UUID receiverCompanyId,

        @Schema(description = "상품 ID", example = "22222222-2222-2222-2222-222222222222")
        UUID productId,

        @Schema(description = "주문 수량", example = "10")
        Integer quantity,

        @Schema(description = "배송 ID", example = "66666666-6666-6666-6666-666666666666", nullable = true)
        UUID deliveryId,

        @Schema(description = "주문 상태", example = "DELIVERY_REQUESTED")
        OrderStatus status,

        @Schema(description = "요청사항", example = "문 앞에 놓아주세요.", nullable = true)
        String requestMessage,

        @Schema(description = "납품 기한 일시", example = "2026-08-20T09:00:00Z")
        Instant dueDate,

        @Schema(description = "주문 취소 일시", example = "2026-08-13T09:00:00Z", nullable = true)
        Instant canceledAt,

        @Schema(description = "주문 취소 사유", example = "고객 요청으로 인한 취소", nullable = true)
        String canceledReason,

        @Schema(description = "생성 일시", example = "2026-08-13T00:00:00Z")
        Instant createdAt,

        @Schema(description = "수정 일시", example = "2026-08-13T01:00:00Z", nullable = true)
        Instant updatedAt
) {
    public static OrderDetailResponse from(Order order) {
        return new OrderDetailResponse(
                order.getId(),
                order.getReceiverCompanyId(),
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                order.getStatus(),
                order.getRequestMessage(),
                order.getDueDate(),
                order.getCanceledAt(),
                order.getCanceledReason(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
