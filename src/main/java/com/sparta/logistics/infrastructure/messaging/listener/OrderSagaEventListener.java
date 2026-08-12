package com.sparta.logistics.infrastructure.messaging.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.logistics.application.command.service.OrderSagaService;
import com.sparta.logistics.infrastructure.messaging.event.delivery.*;
import com.sparta.logistics.infrastructure.messaging.event.hub.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.sparta.logistics.infrastructure.messaging.event.order.OrderEventConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSagaEventListener {

    private final ObjectMapper objectMapper;
    private final OrderSagaService orderSagaService;

    @RabbitListener(queues = "${message.queue.order:order.queue}")
    public void listen(String message) throws Exception {
        JsonNode root = objectMapper.readTree(message);
        String eventType = root.path("header").path("eventType").asText();
        JsonNode payload = root.path("payload");

        switch (eventType) {
            case INVENTORY_DEDUCTED_EVENT -> {
                InventoryDeductedPayload event = objectMapper.treeToValue(payload, InventoryDeductedPayload.class);
                orderSagaService.handleInventoryDeducted(event.orderId());
            }
            case INVENTORY_DEDUCT_FAILED_EVENT -> {
                InventoryDeductFailedPayload event = objectMapper.treeToValue(payload, InventoryDeductFailedPayload.class);
                orderSagaService.handleInventoryDeductFailed(event.orderId(), event.reason());
            }
            case INVENTORY_RESTORED_EVENT -> {
                InventoryRestoredPayload event = objectMapper.treeToValue(payload, InventoryRestoredPayload.class);
                orderSagaService.handleInventoryRestored(event.orderId());
            }
            case INVENTORY_RESTORE_FAILED_EVENT -> {
                InventoryRestoreFailedPayload event = objectMapper.treeToValue(payload, InventoryRestoreFailedPayload.class);
                orderSagaService.handleInventoryRestoreFailed(event.orderId(), event.reason());
            }
            case DELIVERY_CREATED_EVENT -> {
                DeliveryCreatedPayload event = objectMapper.treeToValue(payload, DeliveryCreatedPayload.class);
                orderSagaService.handleDeliveryCreated(event.orderId(), event.deliveryId());
            }
            case DELIVERY_CREATE_FAILED_EVENT -> {
                DeliveryCreateFailedPayload event = objectMapper.treeToValue(payload, DeliveryCreateFailedPayload.class);
                orderSagaService.handleDeliveryCreateFailed(event.orderId(), event.reason());
            }
            case DELIVERY_CANCELED_EVENT -> {
                DeliveryCanceledPayload event = objectMapper.treeToValue(payload, DeliveryCanceledPayload.class);
                orderSagaService.handleDeliveryCanceled(event.orderId());
            }
            case DELIVERY_CANCEL_FAILED_EVENT -> {
                DeliveryCancelFailedPayload event = objectMapper.treeToValue(payload, DeliveryCancelFailedPayload.class);
                orderSagaService.handleDeliveryCancelFailed(event.orderId(), event.reason());
            }
            default -> log.warn("Unsupported saga event type. eventType={}", eventType);
        }
    }
}
