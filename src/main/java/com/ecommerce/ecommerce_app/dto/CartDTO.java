package com.ecommerce.ecommerce_app.dto;

import com.ecommerce.ecommerce_app.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("cartItems")
public class CartDTO {
    private int id;
    private UserDTO user;
    private List<CartItemDTO> cartItems;
    private Double totalPrice;
}