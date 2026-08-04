package com.sparta.logistics.domain.entity;

import com.sparta.logistics.infrastructure.persistence.jpa.entity.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseUpdatableEntity {

    @Column(name = "receiver_company_id", nullable = false, columnDefinition = "UUID")
    private UUID receiverCompanyId;

    @Column(name = "product_id", nullable = false, columnDefinition = "UUID")
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "delivery_id", columnDefinition = "UUID")
    private UUID deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "request_message", columnDefinition = "TEXT")
    private String requestMessage;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Column(name = "canceled_at")
    private Instant canceledAt;

    @Column(name = "canceled_reason", columnDefinition = "TEXT")
    private String canceledReason;

}
