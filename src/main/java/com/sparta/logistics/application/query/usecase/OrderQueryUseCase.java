package com.sparta.logistics.application.query.usecase;

import com.sparta.logistics.application.query.dto.OrderDetailResponse;
import com.sparta.logistics.application.query.dto.OrderSearchCondition;
import com.sparta.logistics.application.query.dto.OrderSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderQueryUseCase {
    OrderDetailResponse getOrderDetail(UUID orderId);
    Page<OrderSearchResponse> searchOrder(OrderSearchCondition condition, Pageable pageable);
}
