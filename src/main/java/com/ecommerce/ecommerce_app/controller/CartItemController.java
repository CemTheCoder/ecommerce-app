package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.CartItemDTO;
import com.ecommerce.ecommerce_app.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/cart-item/{id}")
    public CartItemDTO getCartItem(@PathVariable int id) {
        return this.cartItemService.getCartItem(id);
    }

    @GetMapping("/cart-items")
    public List<CartItemDTO> getCartItems() {
        return this.cartItemService.getCartItems();
    }

    @PostMapping("/cart-item")
    public CartItemDTO createCartItem(@RequestBody CartItemDTO cartItemDTO) {
        return this.cartItemService.createCartItem(cartItemDTO);
    }

    @PutMapping("/cart-item/{id}")
    public CartItemDTO updateCartItem(@PathVariable int id, @RequestBody CartItemDTO cartItemDTO) {
        return this.cartItemService.updateCartItem(id, cartItemDTO);
    }

    @DeleteMapping("/cart-item/{id}")
    public void deleteCartItem(@PathVariable int id) {
        this.cartItemService.deleteCartItem(id);
    }
}