package com.sparta.logistics.infrastructure.feign.dto;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.domain.entity.Order;

import java.util.UUID;

public record HubStockRequest(
        UUID productId,
        UUID hubId,
        Integer quantity
) {
    // 주문 생성 중 재고 차감 시 사용
    public static HubStockRequest from(CreateOrderCommand command) {
        return new HubStockRequest(
                command.productId(),
                command.departureHubId(),
                command.quantity()
        );
    }

    // 주문 취소 시 재고 복구에 사용
    public static HubStockRequest from(Order order){
        return new HubStockRequest(
                order.getProductId(),
                order.getDepartureHubId(),
                order.getQuantity()
        );
    }
}