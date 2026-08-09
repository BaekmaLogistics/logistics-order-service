package com.sparta.logistics.infrastructure.feign.dto.delivery;

import com.sparta.logistics.application.command.dto.CreateOrderCommand;

import java.util.UUID;

public record CreateDeliveryRequest(
        UUID orderId,
        UUID departureHubId,
        UUID destinationHubId,
        String deliveryAddress,
        String receiverName,
        String receiverSlackId
) {
    public static CreateDeliveryRequest from(
            UUID orderId,
            CreateOrderCommand command
    ) {
        return new CreateDeliveryRequest(
                orderId,
                command.departureHubId(),
                command.destinationHubId(),
                command.deliveryAddress(),
                command.receiverName(),
                command.receiverSlackId()
        );
    }
}
