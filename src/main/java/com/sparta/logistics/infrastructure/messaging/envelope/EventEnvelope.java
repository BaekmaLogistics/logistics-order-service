package com.sparta.logistics.infrastructure.messaging.envelope;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T>(
        EventHeader header,
        T payload
) {
    public static <T> EventEnvelope<T> of(String eventType, T payload, UUID actorId) {
        EventHeader header = new EventHeader(
                UUID.randomUUID().toString(),
                actorId,
                eventType,
                Instant.now(),
                "v1"
        );
        return new EventEnvelope<>(header, payload);
    }
}
