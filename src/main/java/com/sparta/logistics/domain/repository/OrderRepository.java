package com.sparta.logistics.domain.repository;

import com.sparta.logistics.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByIdAndDeletedAtIsNull(UUID id);
}
