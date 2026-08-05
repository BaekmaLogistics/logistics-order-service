package com.sparta.logistics.application.command.service;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;
import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.application.command.dto.UpdateOrderCommand;
import com.sparta.logistics.application.command.usecase.OrderCommandUseCase;
import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.repository.OrderRepository;
import com.sparta.logistics.presentation.common.dto.response.ErrorResponseCode;
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


    @Override
    public UUID createOrder(CreateOrderCommand command) {
        Order order = Order.create(
                command.receiverCompanyId(),
                command.productId(),
                command.quantity(),
                command.requestMessage(),
                command.dueDate()
        );

        Order savedOrder = orderRepository.save(order);
        log.info("Order created : {}", savedOrder.getId());
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
        order.cancel(command.canceledReason());
        log.info("Order canceled success :{} {}", command.orderId(), order.getStatus());
    }
}
