package com.sparta.logistics.infrastructure.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.sparta.logistics.infrastructure.messaging.event.order.OrderEventConstants.*;

@Configuration
public class QueueConfig {
    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.delivery}")
    private String queueDelivery;
    @Value("${message.queue.hub}")
    private String queueHub;
    @Value("${message.queue.notification}")
    private String queueNotification;
    @Value("${message.queue.order:order.queue}")
    private String queueOrder;

    @Bean
    public TopicExchange exchange() { return new TopicExchange(exchange); }

    @Bean public Queue queueDelivery() { return new Queue(queueDelivery); }
    @Bean public Queue queueHub() { return new Queue(queueHub); }
    @Bean public Queue queueNotification() { return new Queue(queueNotification); }
    @Bean public Queue queueOrder() { return new Queue(queueOrder); }

    @Bean public Binding bindingDelivery() { return BindingBuilder.bind(queueDelivery()).to(exchange()).with(queueDelivery); }
    @Bean public Binding bindingHub() { return BindingBuilder.bind(queueHub()).to(exchange()).with(queueHub); }
    @Bean public Binding bindingNotification() { return BindingBuilder.bind(queueNotification()).to(exchange()).with(queueNotification); }
    @Bean public Binding bindingOrderInventoryDeducted() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(INVENTORY_DEDUCTED_ROUTING_KEY); }
    @Bean public Binding bindingOrderInventoryDeductFailed() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(INVENTORY_DEDUCT_FAILED_ROUTING_KEY); }
    @Bean public Binding bindingOrderInventoryRestored() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(INVENTORY_RESTORED_ROUTING_KEY); }
    @Bean public Binding bindingOrderInventoryRestoreFailed() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(INVENTORY_RESTORE_FAILED_ROUTING_KEY); }
    @Bean public Binding bindingOrderDeliveryCreated() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(DELIVERY_CREATED_ROUTING_KEY); }
    @Bean public Binding bindingOrderDeliveryCreateFailed() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(DELIVERY_CREATE_FAILED_ROUTING_KEY); }
    @Bean public Binding bindingOrderDeliveryCanceled() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(DELIVERY_CANCELED_ROUTING_KEY); }
    @Bean public Binding bindingOrderDeliveryCancelFailed() { return BindingBuilder.bind(queueOrder()).to(exchange()).with(DELIVERY_CANCEL_FAILED_ROUTING_KEY); }

}
