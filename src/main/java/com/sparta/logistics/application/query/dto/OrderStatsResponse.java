package com.sparta.logistics.application.query.dto;

import com.sparta.logistics.domain.model.OrderStatus;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public record OrderStatsResponse(
        long totalCount,
        Map<OrderStatus, Long> statusCount,
        Map<UUID, Long> receiverCompanyCount,
        Map<LocalDate, Long> dailyCounts
) {
}
