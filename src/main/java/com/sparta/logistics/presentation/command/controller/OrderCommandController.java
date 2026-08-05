package com.sparta.logistics.presentation.command.controller;

import com.sparta.logistics.application.command.usecase.OrderCommandUseCase;
import com.sparta.logistics.presentation.command.dto.CreateOrderRequest;
import com.sparta.logistics.presentation.command.dto.UpdateOrderRequest;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderCommandController {

    private final OrderCommandUseCase orderCommandUseCase;

    @PostMapping
    public ResponseEntity<GeneralResponse<UUID>> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        UUID orderId = orderCommandUseCase.createOrder(request.toCommand());

        return GeneralResponse.toResponseEntity(GeneralResponseCode.CREATED, orderId);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<GeneralResponse<Void>> updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody UpdateOrderRequest request
    ) {
        orderCommandUseCase.updateOrder(request.toCommand(orderId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }
}
