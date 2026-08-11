package com.sparta.logistics.application.command.service;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;
import com.sparta.logistics.application.command.dto.ChangeOrderStatusCommand;
import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.application.command.dto.UpdateOrderCommand;
import com.sparta.logistics.application.command.usecase.OrderCommandUseCase;
import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.model.OrderStatus;
import com.sparta.logistics.domain.repository.OrderRepository;
import com.sparta.logistics.infrastructure.feign.dto.delivery.*;
import com.sparta.logistics.infrastructure.feign.dto.hub.HubStockRequest;
import com.sparta.logistics.presentation.common.dto.response.ErrorResponseCode;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import com.sparta.logistics.presentation.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderCommandService implements OrderCommandUseCase {
    private final OrderRepository orderRepository;
    private final OrderExternalService orderExternalService;
    private final OrderOutboxService orderOutboxService;

    @Override
    @Transactional(noRollbackFor = ApiException.class)
    public UUID createOrder(CreateOrderCommand command) {
        orderExternalService.getProduct(command.productId());

        Order order = Order.create(
                command.ordererUserId(),
                command.departureHubId(),
                command.receiverCompanyId(),
                command.productId(),
                command.quantity(),
                command.requestMessage(),
                command.dueDate()
        );

        Order savedOrder = orderRepository.save(order);

        HubStockRequest stockRequest = HubStockRequest.from(savedOrder);

        try {
            orderExternalService.decreaseStock(stockRequest);
        } catch (Exception e) {
            log.error("Stock decrease failed. request={}", stockRequest, e);

            savedOrder.fail();
            throw new ApiException(ErrorResponseCode.ORDER_STOCK_DECREASE_FAILED);
        }

        try {
            GeneralResponse<DeliveryResponse> deliveryResponse =
                    orderExternalService.createDelivery(CreateDeliveryRequest.from(savedOrder.getId(), command));

            savedOrder.assignDelivery(deliveryResponse.data().id());
        } catch (Exception e) {
            savedOrder.fail();
            try {
                orderExternalService.increaseStock(stockRequest);
            } catch (Exception ex) {
                throw new ApiException(ErrorResponseCode.ORDER_STOCK_RESTORE_FAILED);
            }
            throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_CREATE_FAILED);
        }

        orderOutboxService.saveOrderCreatedEvent(savedOrder);

        log.info("Order created : {}", savedOrder.getId());
        log.info("Delivery created : {}", savedOrder.getDeliveryId());
        return savedOrder.getId();
    }

    @Override
    public void updateOrder(UpdateOrderCommand command) {
        Order order = findOrder(command.orderId());

        log.info("Order found : {}", order.getQuantity());
        order.update(
                command.quantity(),
                command.requestMessage(),
                command.dueDate()
        );

        log.info("Order updated : {}", order.getQuantity());
    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(ErrorResponseCode.ORDER_NOT_FOUND));

    }

    @Override
    public void cancelOrder(CancelOrderCommand command) {
        Order order = findOrder(command.orderId());
        order.validateCancellable();

        if (order.getDeliveryId() != null) {
            try {
                orderExternalService.cancelDelivery(
                        order.getDeliveryId(),
                        CancelDeliveryRequest.from(command)
                );
            } catch (Exception e) {
                throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_CANCEL_FAILED);
            }
        }

        try {
            orderExternalService.increaseStock(HubStockRequest.from(order));
        } catch (Exception e) {
            throw new ApiException(ErrorResponseCode.ORDER_STOCK_RESTORE_FAILED);
        }

        order.cancel(command.canceledReason());
        orderOutboxService.saveOrderCanceledEvent(order);

        log.info("Order canceled success :{} {}", command.orderId(), order.getStatus());
    }

    @Override
    public void deleteOrder(UUID orderId, UUID deletedBy) {
        Order order = findOrder(orderId);

        order.delete(deletedBy);
        log.info("Order delete success : {}", orderId);
        log.info("Order deletedBy : {} {}", deletedBy, order.getDeletedAt());
    }

    @Override
    public void changeOrderStatus(ChangeOrderStatusCommand command) {
        Order order = findOrder(command.orderId());

        if (order.getDeliveryId() == null) {
            throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_NOT_ASSIGNED);
        }

        GeneralResponse<DeliveryStatusResponse> response;
        try {
            response = orderExternalService.getDeliveryStatus(order.getDeliveryId());
        } catch (Exception e) {
            throw new ApiException(ErrorResponseCode.ORDER_DELIVERY_STATUS_LOOKUP_FAILED);
        }

        validateDeliveryStatusForOrderStatus(response.data().status(), command.status());

        order.changeStatus(command.status());

        if (command.status() == OrderStatus.COMPLETED) {
            orderOutboxService.saveOrderCompletedEvent(order);
        }

        log.info("주문 상태가 변경되었습니다 : {} {}", command.orderId(), order.getStatus());
    }

    private void validateDeliveryStatusForOrderStatus(
            DeliveryStatus deliveryStatus,
            OrderStatus nextOrderStatus
    ) {
        boolean valid = switch (nextOrderStatus) {
            case DELIVERY_REQUESTED -> deliveryStatus == DeliveryStatus.HUB_WAITING;
            case DELIVERING -> deliveryStatus == DeliveryStatus.HUB_MOVING
                    || deliveryStatus == DeliveryStatus.HUB_ARRIVED
                    || deliveryStatus == DeliveryStatus.DELIVERING
                    || deliveryStatus == DeliveryStatus.COMPANY_MOVING;
            case COMPLETED -> deliveryStatus == DeliveryStatus.DELIVERED;
            default -> true;
        };

        if (!valid) {
            throw new ApiException(ErrorResponseCode.ORDER_CANNOT_CHANGE_STATUS);
        }
    }
}
