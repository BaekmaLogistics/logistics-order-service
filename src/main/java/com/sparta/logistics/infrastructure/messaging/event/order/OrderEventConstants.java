package com.sparta.logistics.infrastructure.messaging.event.order;

public final class OrderEventConstants {
    public static final String EXCHANGE = "baekma.exchange";
    public static final String AGGREGATE_TYPE = "ORDER";

    public static final String ORDER_CREATED_EVENT = "OrderCreatedEvent";
    public static final String ORDER_CANCELED_EVENT = "OrderCanceledEvent";
    public static final String ORDER_COMPLETED_EVENT = "OrderCompletedEvent";

    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    public static final String ORDER_CANCELED_ROUTING_KEY = "order.canceled";
    public static final String ORDER_COMPLETED_ROUTING_KEY = "order.completed";

    private OrderEventConstants() {
    }
}
