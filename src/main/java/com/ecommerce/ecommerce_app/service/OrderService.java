package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.OrderDTO;
import com.ecommerce.ecommerce_app.dto.UserDTO;
import com.ecommerce.ecommerce_app.entity.*;
import com.ecommerce.ecommerce_app.repository.CartRepository;
import com.ecommerce.ecommerce_app.repository.OrderItemRepository;
import com.ecommerce.ecommerce_app.repository.OrderRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ModelMapper modelMapper,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        return this.modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO convertToDTO(Order order) {
        return this.modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO getOrder(int id) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertToDTO(order);
    }

    public List<OrderDTO> getOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = this.userRepository.findById(orderDTO.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = this.cartRepository.findByUserId(orderDTO.getUser().getId())
            .orElseThrow(() -> new RuntimeException("Cart not found"));


        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderItems(new ArrayList<>());

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return convertToDTO(savedOrder);
    }

    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (orderDTO.getUser() != null) {
            order.setUser(modelMapper.map(orderDTO.getUser(), User.class));
        }

        if (orderDTO.getTotalPrice() != null) {
            order.setTotalPrice(orderDTO.getTotalPrice());
        }

        if (orderDTO.getOrderItems() != null) {
            Type listType = new TypeToken<List<OrderItem>>() {}.getType();
            order.setOrderItems(modelMapper.map(orderDTO.getOrderItems(), listType));
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order bulunamadı!"));

        if(order.getUser() != null){
            User user = order.getUser();
            user.getOrders().remove(order);
            userRepository.save(user);
        }

        order.getOrderItems().clear();

        orderRepository.delete(order);
    }
}
