package com.sparta.logistics.presentation.command.controller;

import com.sparta.logistics.application.command.usecase.OrderCommandUseCase;
import com.sparta.logistics.presentation.command.dto.CancelOrderRequest;
import com.sparta.logistics.presentation.command.dto.ChangeOrderStatusRequest;
import com.sparta.logistics.presentation.command.dto.CreateOrderRequest;
import com.sparta.logistics.presentation.command.dto.UpdateOrderRequest;
import com.sparta.logistics.presentation.common.constant.HeaderConstants;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.common.code.GeneralResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order Command", description = "주문 생성, 수정, 취소, 삭제, 상태 변경 API")
@RequestMapping("/api/v1/orders")
public class OrderCommandController {

    private final OrderCommandUseCase orderCommandUseCase;

    @Operation(summary = "주문 생성", description = "주문을 생성하고 주문 생성 이벤트를 Outbox에 저장합니다.")
    @PostMapping
    public ResponseEntity<GeneralResponse<UUID>> createOrder(
            @RequestHeader(name = HeaderConstants.USER_ID) UUID userId,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        UUID orderId = orderCommandUseCase.createOrder(request.toCommand(userId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.CREATED, orderId);
    }

    @Operation(summary = "주문 수정", description = "배송 시작 전 주문 수량, 요청사항, 납품 기한을 수정합니다.")
    @PatchMapping("/{orderId}")
    public ResponseEntity<GeneralResponse<Void>> updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody UpdateOrderRequest request
    ) {
        orderCommandUseCase.updateOrder(request.toCommand(orderId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }

    @Operation(summary = "주문 취소", description = "주문을 취소하고 주문 취소 이벤트를 Outbox에 저장합니다.")
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<GeneralResponse<Void>> cancelOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody CancelOrderRequest request
    ) {
        orderCommandUseCase.cancelOrder(request.toCommand(orderId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }

    @Operation(summary = "주문 삭제", description = "주문을 논리 삭제합니다.")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<GeneralResponse<Void>> deleteOrder(
            @PathVariable UUID orderId,
            @RequestHeader(name = HeaderConstants.USER_ID) UUID deletedBy
    ) {
        orderCommandUseCase.deleteOrder(orderId, deletedBy);

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }

    @Operation(summary = "주문 상태 변경", description = "배송 상태를 검증한 뒤 주문 상태를 변경합니다.")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<GeneralResponse<Void>> changeOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody ChangeOrderStatusRequest request
    ) {
        orderCommandUseCase.changeOrderStatus(request.toCommand(orderId));

        return GeneralResponse.toResponseEntity(GeneralResponseCode.OK, null);
    }
}