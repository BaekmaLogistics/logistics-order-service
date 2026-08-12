package com.sparta.logistics.presentation.command.controller;

import com.sparta.logistics.application.command.usecase.OrderCommandUseCase;
import com.sparta.logistics.presentation.command.dto.CancelOrderRequest;
import com.sparta.logistics.presentation.command.dto.ChangeOrderStatusRequest;
import com.sparta.logistics.presentation.command.dto.CreateOrderRequest;
import com.sparta.logistics.presentation.command.dto.UpdateOrderRequest;
import com.sparta.logistics.presentation.common.constant.HeaderConstants;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.common.code.GeneralResponseCode;
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
            @RequestHeader(name = HeaderConstants.USER_ID) UUID userId,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        UUID orderId = orderCommandUseCase.createOrder(request.toCommand(userId));

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

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<GeneralResponse<Void>> cancelOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody CancelOrderRequest request
    ) {
        orderCommandUseCase.cancelOrder(request.toCommand(orderId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<GeneralResponse<Void>> deleteOrder(
            @PathVariable UUID orderId,
            @RequestHeader(name = HeaderConstants.USER_ID) UUID deletedBy
    ) {
        orderCommandUseCase.deleteOrder(orderId, deletedBy);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<GeneralResponse<Void>> changeOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody ChangeOrderStatusRequest request
    ) {
        orderCommandUseCase.changeOrderStatus(request.toCommand(orderId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }
}