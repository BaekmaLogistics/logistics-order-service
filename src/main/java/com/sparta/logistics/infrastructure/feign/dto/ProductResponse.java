package com.sparta.logistics.infrastructure.feign.dto;

import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        UUID companyId,
        String companyName,
        Instant createdAt,
        Instant updatedAt
) {
}
