package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "주문 검색 조건")
public record OrderSearchCondition(
        @Schema(description = "수령 업체 ID", example = "11111111-1111-1111-1111-111111111111", nullable = true)
        UUID receiverCompanyId,

        @Schema(description = "상품 ID", example = "22222222-2222-2222-2222-222222222222", nullable = true)
        UUID productId,

        @Schema(description = "배송 ID", example = "66666666-6666-6666-6666-666666666666", nullable = true)
        UUID deliveryId,

        @Schema(description = "주문 상태", example = "PENDING", nullable = true)
        OrderStatus status,

        @Schema(description = "검색 시작 일시", example = "2026-08-01T00:00:00Z", nullable = true)
        Instant startDate,

        @Schema(description = "검색 종료 일시", example = "2026-08-31T23:59:59Z", nullable = true)
        Instant endDate
) {
}
