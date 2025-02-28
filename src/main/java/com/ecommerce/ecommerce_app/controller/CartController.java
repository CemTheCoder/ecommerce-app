package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.CartDTO;
import com.ecommerce.ecommerce_app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {


    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/{id}")
    public CartDTO getCart(@PathVariable int id) {
        return this.cartService.getCart(id);
    }

    @GetMapping("/cart")
    public List<CartDTO> getCarts() {
        return this.cartService.getCarts();
    }

    @PostMapping("/cart")
    public CartDTO createCart(@RequestBody CartDTO cartDTO) {
        return this.cartService.createCart(cartDTO);
    }

    @PutMapping("/cart/{id}")
    public CartDTO updateCart(@PathVariable int id, @RequestBody CartDTO cartDTO) {
        return this.cartService.updateCart(id, cartDTO);
    }

    @DeleteMapping("/cart/{id}")
    public void deleteCart(@PathVariable int id) {
        this.cartService.deleteCart(id);
    }
}