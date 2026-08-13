package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Schema(description = "주문 통계 응답")
public record OrderStatsResponse(
        @Schema(description = "전체 주문 수", example = "100")
        long totalCount,

        @Schema(description = "주문 상태별 주문 수")
        Map<OrderStatus, Long> statusCount,

        @Schema(description = "수령 업체별 주문 수")
        Map<UUID, Long> receiverCompanyCount,

        @Schema(description = "일자별 주문 수")
        Map<LocalDate, Long> dailyCounts
) {
}
