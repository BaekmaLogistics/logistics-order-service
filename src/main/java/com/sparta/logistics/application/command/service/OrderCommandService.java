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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
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
                command.status(),
                command.requestMessage(),
                command.dueDate()
        );

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }
}
