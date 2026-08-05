package com.sparta.logistics.application.command.dto;

import java.time.Instant;
import java.util.UUID;

public record CreateOrderCommand(
        UUID receiverCompanyId,
        UUID productId,
        Integer quantity,
        String requestMessage,
        Instant dueDate
) {
}
