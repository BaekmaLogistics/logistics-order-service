package com.sparta.logistics.application.command.usecase;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;
import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.application.command.dto.UpdateOrderCommand;

import java.util.UUID;

public interface OrderCommandUseCase {
    UUID createOrder(CreateOrderCommand command);
    void updateOrder(UpdateOrderCommand command);
    void cancelOrder(CancelOrderCommand command);
    void deleteOrder(UUID orderId, UUID deletedBy);
}
