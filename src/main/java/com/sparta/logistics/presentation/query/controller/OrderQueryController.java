package com.sparta.logistics.presentation.query.controller;

import com.sparta.logistics.application.query.dto.OrderDetailResponse;
import com.sparta.logistics.application.query.usecase.OrderQueryUseCase;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
