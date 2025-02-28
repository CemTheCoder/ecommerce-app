package com.ecommerce.ecommerce_app.dto;


import com.ecommerce.ecommerce_app.entity.OrderItem;
import com.ecommerce.ecommerce_app.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("orderItems")
public class OrderDTO {
    private int id;
    private UserDTO user;
    private List<OrderItemDTO> orderItems;
    private Double totalPrice;
    private String status;
    private LocalDateTime orderDate;
}
