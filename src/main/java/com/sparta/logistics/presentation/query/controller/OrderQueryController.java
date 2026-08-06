package com.sparta.logistics.presentation.query.controller;

import com.sparta.logistics.application.query.dto.OrderDetailResponse;
import com.sparta.logistics.application.query.dto.OrderSearchCondition;
import com.sparta.logistics.application.query.dto.OrderSearchResponse;
import com.sparta.logistics.application.query.dto.OrderStatsResponse;
import com.sparta.logistics.application.query.usecase.OrderQueryUseCase;
import com.sparta.logistics.domain.model.OrderStatus;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderQueryController {

    private final OrderQueryUseCase orderQueryUseCase;

    @GetMapping("/{orderId}")
    public ResponseEntity<GeneralResponse<OrderDetailResponse>> getOrderDetail(
            @PathVariable UUID orderId
    ) {
        OrderDetailResponse response = orderQueryUseCase.getOrderDetail(orderId);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, response);
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<Page<OrderSearchResponse>>> searchOrders(
            @RequestParam(required = false) UUID receiverCompanyId,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID deliveryId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        OrderSearchCondition condition = new OrderSearchCondition(
                receiverCompanyId,
                productId,
                deliveryId,
                status,
                startDate,
                endDate
        );

        Page<OrderSearchResponse> response = orderQueryUseCase.searchOrder(condition, pageable);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, response);
    }

    @GetMapping("/stats")
    public ResponseEntity<GeneralResponse<OrderStatsResponse >> getOrderStats(
            @RequestParam(required = false) UUID receiverCompanyId,
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID deliveryId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate
    ) {
        OrderSearchCondition condition = new OrderSearchCondition(
                receiverCompanyId,
                productId,
                deliveryId,
                status,
                startDate,
                endDate
        );

        OrderStatsResponse response = orderQueryUseCase.getOrderStats(condition);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, response);
    }
}
