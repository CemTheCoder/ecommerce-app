package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.OrderDTO;
import com.ecommerce.ecommerce_app.entity.Order;
import com.ecommerce.ecommerce_app.entity.OrderItem;
import com.ecommerce.ecommerce_app.entity.User;
import com.ecommerce.ecommerce_app.repository.OrderItemRepository;
import com.ecommerce.ecommerce_app.repository.OrderRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ModelMapper modelMapper,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
        Order order = new Order();
        order.setUser(modelMapper.map(orderDTO.getUser(), User.class));
        if(orderDTO.getOrderItems() != null) {
            Type listType = new TypeToken<List<OrderItem>>() {}.getType();
            order.setOrderItems(modelMapper.map(orderDTO.getOrderItems(), listType));
        }
        order.setTotalPrice(orderDTO.getTotalPrice());
        Order savedOrder = this.orderRepository.save(order);
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
