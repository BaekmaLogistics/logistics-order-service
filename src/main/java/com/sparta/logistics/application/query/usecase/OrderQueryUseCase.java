package com.sparta.logistics.application.query.usecase;

import com.sparta.logistics.application.query.dto.OrderDetailResponse;

import java.util.UUID;

public interface OrderQueryUseCase {
    OrderDetailResponse getOrderDetail(UUID orderId);
}
