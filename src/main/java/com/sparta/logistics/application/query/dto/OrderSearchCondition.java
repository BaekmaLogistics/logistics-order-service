package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record OrderSearchCondition(
        UUID receiverCompanyId,
        UUID productId,
        UUID deliveryId,
        OrderStatus status,
        Instant startDate,
        Instant endDate
) {
}
