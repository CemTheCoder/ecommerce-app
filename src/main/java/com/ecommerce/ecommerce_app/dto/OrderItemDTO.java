package com.ecommerce.ecommerce_app.dto;

import com.ecommerce.ecommerce_app.entity.Order;
import com.ecommerce.ecommerce_app.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private int id;
    private OrderDTO order;
    private ProductDTO product;
    private int quantity;
    private Double price;
}
