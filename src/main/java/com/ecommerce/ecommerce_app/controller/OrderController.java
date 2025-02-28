package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.OrderDTO;
import com.ecommerce.ecommerce_app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public OrderDTO getOrder(@PathVariable int id) {
        return this.orderService.getOrder(id);
    }

    @GetMapping("/order")
    public List<OrderDTO> getOrders() {
        return this.orderService.getOrders();
    }

    @PostMapping("/order")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return this.orderService.createOrder(orderDTO);
    }

    @PutMapping("/order/{id}")
    public OrderDTO updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        return this.orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable int id) {
        this.orderService.deleteOrder(id);
    }
}
