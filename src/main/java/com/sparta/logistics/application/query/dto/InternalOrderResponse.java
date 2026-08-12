package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.entity.Order;

import java.util.UUID;

public record InternalOrderResponse(
        UUID orderId,
        UUID companyId
) {
    public static InternalOrderResponse from(Order order) {
        return new InternalOrderResponse(
                order.getId(),
                order.getReceiverCompanyId()
        );
    }
}
