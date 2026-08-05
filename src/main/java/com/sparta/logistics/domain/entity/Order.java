package com.sparta.logistics.domain.entity;

import com.sparta.logistics.domain.model.OrderStatus;
import com.sparta.logistics.infrastructure.persistence.jpa.entity.BaseUpdatableEntity;
import com.sparta.logistics.presentation.common.dto.response.ErrorResponseCode;
import com.sparta.logistics.presentation.common.exception.ApiException;
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

    public static Order create(
            UUID receiverCompanyId,
            UUID productId,
            Integer quantity,
            String requestMessage,
            Instant dueDate
    ) {
        validateQuantity(quantity);

        Order order = new Order();
        order.receiverCompanyId = receiverCompanyId;
        order.productId = productId;
        order.quantity = quantity;
        order.status = OrderStatus.PENDING;
        order.requestMessage = requestMessage;
        order.dueDate = dueDate;

        return order;
    }

    private static void validateQuantity(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new ApiException(ErrorResponseCode.ORDER_INVALID_QUANTITY);
        }
    }


    public void update(
            Integer quantity,
            String requestMessage,
            Instant dueDate
    ) {
        validateUpdatable();
        validateQuantity(quantity);

        this.quantity = quantity;
        this.requestMessage = requestMessage;
        this.dueDate = dueDate;
    }

    private void validateUpdatable() {
        if (this.status != OrderStatus.PENDING) {
            throw new ApiException(ErrorResponseCode.ORDER_CANNOT_BE_UPDATED);
        }
    }
}
