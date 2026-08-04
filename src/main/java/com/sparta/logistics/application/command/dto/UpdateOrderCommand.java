package com.sparta.logistics.application.command.dto;

import java.time.Instant;
import java.util.UUID;

public record UpdateOrderCommand(
        UUID orderId,
        Integer quantity,
        String requestMessage,
        Instant dueDate
) {
}
