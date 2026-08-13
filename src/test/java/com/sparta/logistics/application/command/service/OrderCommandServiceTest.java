package com.sparta.logistics.application.command.service;

import com.sparta.logistics.application.command.dto.CancelOrderCommand;
import com.sparta.logistics.application.command.dto.ChangeOrderStatusCommand;
import com.sparta.logistics.application.command.dto.CreateOrderCommand;
import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.model.OrderStatus;
import com.sparta.logistics.domain.repository.OrderRepository;
import com.sparta.logistics.infrastructure.feign.dto.delivery.DeliveryStatus;
import com.sparta.logistics.infrastructure.feign.dto.delivery.DeliveryStatusResponse;
import com.sparta.logistics.infrastructure.feign.dto.product.ProductResponse;
import com.sparta.logistics.presentation.common.dto.response.GeneralResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderExternalService orderExternalService;

    @Mock
    private OrderOutboxService orderOutboxService;

    @InjectMocks
    private OrderCommandService orderCommandService;

    @Test
    @DisplayName("주문 생성 성공 시 주문 생성 Outbox 이벤트를 저장한다")
    void createOrder_savesOrderCreatedOutboxEvent() {
        UUID orderId = UUID.randomUUID();
        CreateOrderCommand command = createOrderCommand();

        when(orderExternalService.getProduct(command.productId()))
                .thenReturn(new GeneralResponse<>("OK", new ProductResponse(
                        command.productId(),
                        "테스트 상품",
                        UUID.randomUUID(),
                        "테스트 업체",
                        Instant.now(),
                        Instant.now()
                )));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            ReflectionTestUtils.setField(order, "id", orderId);
            return order;
        });
        UUID savedOrderId = orderCommandService.createOrder(command);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        assertThat(savedOrderId).isEqualTo(orderId);
        verify(orderOutboxService, times(1)).saveOrderCreatedEvent(orderCaptor.capture(), eq(command));
        verify(orderOutboxService, never()).saveOrderCanceledEvent(any(Order.class));
        verify(orderOutboxService, never()).saveOrderCompletedEvent(any(Order.class));
        verify(orderExternalService, never()).decreaseStock(any());
        verify(orderExternalService, never()).createDelivery(any());

        Order capturedOrder = orderCaptor.getValue();
        assertThat(capturedOrder.getId()).isEqualTo(orderId);
        assertThat(capturedOrder.getDeliveryId()).isNull();
        assertThat(capturedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("주문 취소 성공 시 주문 취소 Outbox 이벤트를 저장한다")
    void cancelOrder_savesOrderCanceledOutboxEvent() {
        UUID orderId = UUID.randomUUID();
        Order order = createDeliveryRequestedOrder(orderId, UUID.randomUUID());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderCommandService.cancelOrder(new CancelOrderCommand(orderId, "고객 요청 취소"));

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
        verify(orderOutboxService, times(1)).saveOrderCanceledEvent(order);
        verify(orderOutboxService, never()).saveOrderCreatedEvent(any(Order.class), any(CreateOrderCommand.class));
        verify(orderOutboxService, never()).saveOrderCompletedEvent(any(Order.class));
        verify(orderExternalService, never()).increaseStock(any());
        verify(orderExternalService, never()).cancelDelivery(any(), any());
    }

    @Test
    @DisplayName("주문 상태가 COMPLETED로 변경되면 주문 완료 Outbox 이벤트를 저장한다")
    void changeOrderStatusToCompleted_savesOrderCompletedOutboxEvent() {
        UUID orderId = UUID.randomUUID();
        UUID deliveryId = UUID.randomUUID();
        Order order = createDeliveryRequestedOrder(orderId, deliveryId);
        order.changeStatus(OrderStatus.DELIVERING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderExternalService.getDeliveryStatus(deliveryId))
                .thenReturn(new GeneralResponse<>(
                        "OK",
                        new DeliveryStatusResponse(deliveryId, DeliveryStatus.DELIVERED)
                ));

        orderCommandService.changeOrderStatus(
                new ChangeOrderStatusCommand(orderId, OrderStatus.COMPLETED)
        );

        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        verify(orderOutboxService, times(1)).saveOrderCompletedEvent(order);
        verify(orderOutboxService, never()).saveOrderCreatedEvent(any(Order.class), any(CreateOrderCommand.class));
        verify(orderOutboxService, never()).saveOrderCanceledEvent(any(Order.class));
    }

    private CreateOrderCommand createOrderCommand() {
        return new CreateOrderCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                3,
                "문 앞에 놓아주세요.",
                Instant.now().plusSeconds(86_400),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "서울시 테스트 주소",
                "홍길동",
                "hong-test"
        );
    }

    private Order createDeliveryRequestedOrder(UUID orderId, UUID deliveryId) {
        Order order = Order.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                3,
                "요청사항",
                Instant.now().plusSeconds(86_400)
        );
        ReflectionTestUtils.setField(order, "id", orderId);
        order.assignDelivery(deliveryId);
        return order;
    }
}
