package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.OrderItemDTO;
import com.ecommerce.ecommerce_app.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/order-item/{id}")
    public OrderItemDTO getOrderItem(@PathVariable int id) {
        return this.orderItemService.getOrderItem(id);
    }

    @GetMapping("/order-items")
    public List<OrderItemDTO> getOrderItems() {
        return this.orderItemService.getOrderItems();
    }

    @PostMapping("/order-item")
    public OrderItemDTO createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        return this.orderItemService.createOrderItem(orderItemDTO);
    }

    @PutMapping("/order-item/{id}")
    public OrderItemDTO updateOrderItem(@PathVariable int id, @RequestBody OrderItemDTO orderItemDTO) {
        return this.orderItemService.updateOrderItem(id, orderItemDTO);
    }

    @DeleteMapping("/order-item/{id}")
    public void deleteOrderItem(@PathVariable int id) {
        this.orderItemService.deleteOrderItem(id);
    }
}
