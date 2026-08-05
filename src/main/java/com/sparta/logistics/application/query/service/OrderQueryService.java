package com.sparta.logistics.application.query.service;

import com.sparta.logistics.application.query.dto.OrderDetailResponse;
import com.sparta.logistics.application.query.usecase.OrderQueryUseCase;
import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.repository.OrderRepository;
import com.sparta.logistics.presentation.common.dto.response.ErrorResponseCode;
import com.sparta.logistics.presentation.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService implements OrderQueryUseCase {

    private final OrderRepository orderRepository;

    @Override
    public OrderDetailResponse getOrderDetail(UUID orderId) {
        Order order = orderRepository.findByIdAndDeletedAtIsNull(orderId)
                .orElseThrow(() -> new ApiException(ErrorResponseCode.ORDER_NOT_FOUND));

        return OrderDetailResponse.from(order);
    }
}
