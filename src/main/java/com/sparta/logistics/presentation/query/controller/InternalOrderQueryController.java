package com.sparta.logistics.presentation.query.controller;

import com.sparta.logistics.application.query.dto.InternalOrderResponse;
import com.sparta.logistics.application.query.usecase.OrderQueryUseCase;
import com.sparta.logistics.common.code.GeneralResponseCode;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/v1/orders")
public class InternalOrderQueryController {

    private final OrderQueryUseCase orderQueryUseCase;

    @GetMapping("/{orderId}")
    public ResponseEntity<GeneralResponse<InternalOrderResponse>> getInternalOrder(
            @PathVariable UUID orderId
    ) {
        InternalOrderResponse response = orderQueryUseCase.getInternalOrder(orderId);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, response);
    }
}
