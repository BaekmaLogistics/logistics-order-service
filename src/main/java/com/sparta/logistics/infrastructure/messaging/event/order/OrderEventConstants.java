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

    public static final String INVENTORY_DEDUCTED_EVENT = "InventoryDeductedEvent";
    public static final String INVENTORY_DEDUCT_FAILED_EVENT = "InventoryDeductFailedEvent";
    public static final String INVENTORY_RESTORED_EVENT = "InventoryRestoredEvent";
    public static final String INVENTORY_RESTORE_FAILED_EVENT = "InventoryRestoreFailedEvent";

    public static final String DELIVERY_CREATED_EVENT = "DeliveryCreatedEvent";
    public static final String DELIVERY_CREATE_FAILED_EVENT = "DeliveryCreateFailedEvent";
    public static final String DELIVERY_CANCELED_EVENT = "DeliveryCanceledEvent";
    public static final String DELIVERY_CANCEL_FAILED_EVENT = "DeliveryCancelFailedEvent";

    public static final String INVENTORY_DEDUCTED_ROUTING_KEY = "hub.inventory.deducted";
    public static final String INVENTORY_DEDUCT_FAILED_ROUTING_KEY = "hub.inventory.deduct.failed";
    public static final String INVENTORY_RESTORED_ROUTING_KEY = "hub.inventory.restored";
    public static final String INVENTORY_RESTORE_FAILED_ROUTING_KEY = "hub.inventory.restore.failed";

    public static final String DELIVERY_CREATED_ROUTING_KEY = "delivery.created";
    public static final String DELIVERY_CREATE_FAILED_ROUTING_KEY = "delivery.create.failed";
    public static final String DELIVERY_CANCELED_ROUTING_KEY = "delivery.canceled";
    public static final String DELIVERY_CANCEL_FAILED_ROUTING_KEY = "delivery.cancel.failed";

    private OrderEventConstants() {
    }
}
