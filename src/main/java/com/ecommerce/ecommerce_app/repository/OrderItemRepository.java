package com.ecommerce.ecommerce_app.repository;

import com.ecommerce.ecommerce_app.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    Optional<OrderItem> findById(int id);
}
