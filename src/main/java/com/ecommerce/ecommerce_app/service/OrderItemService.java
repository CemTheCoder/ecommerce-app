package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.OrderItemDTO;
import com.ecommerce.ecommerce_app.entity.Order;
import com.ecommerce.ecommerce_app.entity.OrderItem;
import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.repository.OrderItemRepository;
import com.ecommerce.ecommerce_app.repository.OrderRepository;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            ProductRepository productRepository,
                            ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
        return modelMapper.map(orderItemDTO, OrderItem.class);
    }

    public OrderItemDTO convertToDTO(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    public OrderItemDTO getOrderItem(int id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
        return convertToDTO(orderItem);
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(modelMapper.map(orderItemDTO.getOrder(), Order.class));
        orderItem.setProduct(modelMapper.map(orderItemDTO.getProduct(), Product.class));
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return convertToDTO(savedOrderItem);
    }

    public OrderItemDTO updateOrderItem(int id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));

        if(orderItemDTO.getOrder() != null) {
            orderItem.setOrder(modelMapper.map(orderItemDTO.getOrder(), Order.class));
        }

        if(orderItemDTO.getProduct() != null) {
            orderItem.setProduct(modelMapper.map(orderItemDTO.getProduct(), Product.class));
        }

        if(orderItemDTO.getQuantity() != 0) {
            orderItem.setQuantity(orderItemDTO.getQuantity());
        }

        if(orderItemDTO.getPrice() != null) {
            orderItem.setPrice(orderItemDTO.getPrice());
        }

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return convertToDTO(updatedOrderItem);
    }

    public void deleteOrderItem(int id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));

        Order order = orderItem.getOrder();
        if (order != null) {
            order.getOrderItems().remove(orderItem);
            orderRepository.save(order);
        } else {
            orderItemRepository.delete(orderItem);
        }
    }
}
