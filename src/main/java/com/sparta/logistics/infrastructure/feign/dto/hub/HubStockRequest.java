package com.sparta.logistics.infrastructure.feign.dto.hub;

import com.sparta.logistics.domain.entity.Order;

import java.util.UUID;

public record HubStockRequest(
        UUID orderId,
        UUID productId,
        UUID hubId,
        Integer quantity
) {
    public static HubStockRequest from(Order order) {
        return new HubStockRequest(
                order.getId(),
                order.getProductId(),
                order.getDepartureHubId(),
                order.getQuantity()
        );
    }
}