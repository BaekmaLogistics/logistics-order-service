package com.sparta.logistics.application.command.dto;

import java.util.UUID;

public record CancelOrderCommand(
        UUID orderId,
        String canceledReason
) {
}
