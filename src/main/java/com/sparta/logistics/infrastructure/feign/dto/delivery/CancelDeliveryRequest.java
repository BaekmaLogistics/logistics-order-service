package com.sparta.logistics.infrastructure.feign.dto.delivery;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;

import java.util.UUID;

public record CancelDeliveryRequest(
        UUID orderId,
        String canceledReason
) {
    public static CancelDeliveryRequest from(CancelOrderCommand command) {
        return new CancelDeliveryRequest(
                command.orderId(),
                command.canceledReason()
        );
    }
}
