package com.sparta.logistics.application.command.service;

import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.model.OrderStatus;
import com.sparta.logistics.domain.repository.OrderRepository;
import com.sparta.logistics.presentation.common.dto.response.ErrorResponseCode;
import com.sparta.logistics.presentation.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderSagaService {

    private final OrderRepository orderRepository;

    public void handleInventoryDeducted(UUID orderId) {
        log.info("Inventory deducted event received. orderId={}", orderId);
    }

    public void handleInventoryDeductFailed(UUID orderId, String reason) {
        Order order = findOrder(orderId);
        failIfNotTerminal(order);
        log.warn("Inventory deduct failed. orderId={}, reason={}", orderId, reason);
    }

    public void handleInventoryRestored(UUID orderId) {
        log.info("Inventory restored event received. orderId={}", orderId);
    }

    public void handleInventoryRestoreFailed(UUID orderId, String reason) {
        Order order = findOrder(orderId);
        failIfNotTerminal(order);
        log.warn("Inventory restore failed. orderId={}, reason={}", orderId, reason);
    }

    public void handleDeliveryCreated(UUID orderId, UUID deliveryId) {
        Order order = findOrder(orderId);

        if (order.getDeliveryId() != null) {
            log.info("Delivery already assigned. orderId={}, deliveryId={}", orderId, order.getDeliveryId());
            return;
        }

        order.assignDelivery(deliveryId);
        log.info("Delivery created event handled. orderId={}, deliveryId={}", orderId, deliveryId);
    }

    public void handleDeliveryCreateFailed(UUID orderId, String reason) {
        Order order = findOrder(orderId);
        failIfNotTerminal(order);
        log.warn("Delivery create failed. orderId={}, reason={}", orderId, reason);
    }

    public void handleDeliveryCanceled(UUID orderId) {
        log.info("Delivery canceled event received. orderId={}", orderId);
    }

    public void handleDeliveryCancelFailed(UUID orderId, String reason) {
        Order order = findOrder(orderId);
        failIfNotTerminal(order);
        log.warn("Delivery cancel failed. orderId={}, reason={}", orderId, reason);
    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findByIdAndDeletedAtIsNull(orderId)
                .orElseThrow(() -> new ApiException(ErrorResponseCode.ORDER_NOT_FOUND));
    }

    private void failIfNotTerminal(Order order) {
        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELED) {
            return;
        }
        order.fail();
    }
}
