package com.sparta.logistics.infrastructure.feign.dto;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;

import java.util.UUID;

public record HubStockRequest(
        UUID productId,
        UUID hubId,
        Integer quantity
) {
    public static HubStockRequest from(CreateOrderCommand command) {
        return new HubStockRequest(
                command.productId(),
                command.departureHubId(),
                command.quantity()
        );
    }
}
