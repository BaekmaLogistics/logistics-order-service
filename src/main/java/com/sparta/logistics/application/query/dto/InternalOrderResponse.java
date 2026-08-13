package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "내부 주문 조회 응답")
public record InternalOrderResponse(
        @Schema(description = "주문 ID", example = "55555555-5555-5555-5555-555555555555")
        UUID orderId,

        @Schema(description = "수령 업체 ID", example = "11111111-1111-1111-1111-111111111111")
        UUID companyId
) {
    public static InternalOrderResponse from(Order order) {
        return new InternalOrderResponse(
                order.getId(),
                order.getReceiverCompanyId()
        );
    }
}
