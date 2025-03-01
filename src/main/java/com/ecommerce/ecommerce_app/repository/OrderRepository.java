package com.ecommerce.ecommerce_app.repository;

import com.ecommerce.ecommerce_app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findById(int id);
}
