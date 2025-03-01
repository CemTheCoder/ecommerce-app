package com.ecommerce.ecommerce_app.repository;

import com.ecommerce.ecommerce_app.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findById(int id);

    Optional<Cart> findByUserId(int id);
}
