package com.sparta.logistics.application.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sparta.logistics.domain.entity.Order;
import com.sparta.logistics.domain.entity.OutboxEvent;
import com.sparta.logistics.domain.model.OutboxStatus;
import com.sparta.logistics.domain.repository.OutboxEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {
        OrderOutboxService.class,
        OrderOutboxServiceJpaTest.TestObjectMapperConfig.class,
        OrderOutboxServiceJpaTest.TestJpaConfig.class
})
class OrderOutboxServiceJpaTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("logistics_order_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Autowired
    private OrderOutboxService orderOutboxService;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Test
    @DisplayName("주문 생성 Outbox 이벤트를 p_outbox_events 테이블에 저장한다")
    void saveOrderCreatedEvent_persistsOutboxEventRow() {
        UUID orderId = UUID.randomUUID();
        UUID deliveryId = UUID.randomUUID();
        Order order = createOrder(orderId, deliveryId);

        orderOutboxService.saveOrderCreatedEvent(order);

        List<OutboxEvent> events = outboxEventRepository.findAll();
        assertThat(events).hasSize(1);

        OutboxEvent event = events.get(0);
        assertThat(event.getAggregateType()).isEqualTo("ORDER");
        assertThat(event.getAggregateId()).isEqualTo(orderId);
        assertThat(event.getEventType()).isEqualTo("OrderCreatedEvent");
        assertThat(event.getExchange()).isEqualTo("baekma.exchange");
        assertThat(event.getRoutingKey()).isEqualTo("order.created");
        assertThat(event.getStatus()).isEqualTo(OutboxStatus.PENDING);
        assertThat(event.getRetryCount()).isZero();
        assertThat(event.getPayload())
                .contains("\"eventType\":\"OrderCreatedEvent\"")
                .contains("\"id\":\"" + orderId + "\"")
                .contains("\"deliveryId\":\"" + deliveryId + "\"")
                .contains("\"orderStatus\":\"DELIVERY_REQUESTED\"");
    }

    private Order createOrder(UUID orderId, UUID deliveryId) {
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

    @Configuration
    static class TestObjectMapperConfig {

        @Bean
        ObjectMapper objectMapper() {
            return JsonMapper.builder()
                    .findAndAddModules()
                    .build();
        }
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EntityScan("com.sparta.logistics.domain.entity")
    @EnableJpaRepositories("com.sparta.logistics.domain.repository")
    @EnableJpaAuditing
    static class TestJpaConfig {

        @Bean
        AuditorAware<UUID> auditorProvider() {
            return Optional::empty;
        }
    }
}
