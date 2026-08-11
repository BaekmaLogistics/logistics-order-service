package com.sparta.logistics.application.command.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.entity.OutboxEvent;
import com.sparta.logistics.domain.repository.OutboxEventRepository;
import com.sparta.logistics.infrastructure.messaging.envelope.EventEnvelope;
import com.sparta.logistics.infrastructure.messaging.event.order.OrderCanceledPayload;
import com.sparta.logistics.infrastructure.messaging.event.order.OrderCompletedPayload;
import com.sparta.logistics.infrastructure.messaging.event.order.OrderCreatedPayload;
import com.sparta.logistics.presentation.common.dto.response.ErrorResponseCode;
import com.sparta.logistics.presentation.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.sparta.logistics.infrastructure.messaging.event.order.OrderEventConstants.*;

@Service
@RequiredArgsConstructor
public class OrderOutboxService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public void saveOrderCreatedEvent(Order order) {
        OrderCreatedPayload payload = OrderCreatedPayload.from(order);

        EventEnvelope<OrderCreatedPayload> envelope = EventEnvelope.of(
                ORDER_CREATED_EVENT,
                payload,
                order.getOrdererUserId()
        );

        save(
                order.getId(),
                ORDER_CREATED_EVENT,
                ORDER_CREATED_ROUTING_KEY,
                envelope
        );
    }

    public void saveOrderCanceledEvent(Order order) {
        OrderCanceledPayload payload = OrderCanceledPayload.from(order);

        EventEnvelope<OrderCanceledPayload> envelope = EventEnvelope.of(
                ORDER_CANCELED_EVENT,
                payload,
                order.getOrdererUserId()
        );

        save(
                order.getId(),
                ORDER_CANCELED_EVENT,
                ORDER_CANCELED_ROUTING_KEY,
                envelope
        );
    }

    public void saveOrderCompletedEvent(Order order) {
        OrderCompletedPayload payload = OrderCompletedPayload.from(order);

        EventEnvelope<OrderCompletedPayload> envelope = EventEnvelope.of(
                ORDER_COMPLETED_EVENT,
                payload,
                order.getOrdererUserId()
        );

        save(
                order.getId(),
                ORDER_COMPLETED_EVENT,
                ORDER_COMPLETED_ROUTING_KEY,
                envelope
        );
    }

    private void save(
            UUID orderId,
            String eventType,
            String routingKey,
            EventEnvelope<?> envelope
    ) {
        String payload = serialize(envelope);

        OutboxEvent outboxEvent = OutboxEvent.create(
                AGGREGATE_TYPE,
                orderId,
                eventType,
                EXCHANGE,
                routingKey,
                payload
        );

        outboxEventRepository.save(outboxEvent);
    }

    private String serialize(EventEnvelope<?> envelope) {
        try {
            return objectMapper.writeValueAsString(envelope);
        } catch (JsonProcessingException e) {
            throw new ApiException(ErrorResponseCode.ORDER_OUTBOX_SAVE_FAILED);
        }
    }
}
