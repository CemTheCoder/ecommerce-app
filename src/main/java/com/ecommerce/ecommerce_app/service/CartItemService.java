package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.CartItemDTO;
import com.ecommerce.ecommerce_app.dto.CategoryDTO;
import com.ecommerce.ecommerce_app.entity.Cart;
import com.ecommerce.ecommerce_app.entity.CartItem;
import com.ecommerce.ecommerce_app.entity.Category;
import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.repository.CartItemRepository;
import com.ecommerce.ecommerce_app.repository.CartRepository;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository,
                           CartRepository cartRepository,
                           ProductRepository productRepository,
                           ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public CartItem convertToEntity(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    public CartItemDTO convertToDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

    public CartItemDTO getCartItem(int id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));
        return convertToDTO(cartItem);
    }

    public List<CartItemDTO> getCartItems() {
        return cartItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findById(cartItemDTO.getCart().getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(cartItemDTO.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(product.getPrice() * cartItemDTO.getQuantity());
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        if(cart.getTotalPrice() != null) {
            cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * cartItemDTO.getQuantity());
        }
        if(cart.getTotalPrice() == null) {
            cart.setTotalPrice(product.getPrice() * cartItemDTO.getQuantity());
        }
        this.cartRepository.save(cart);
        return convertToDTO(savedCartItem);
    }

    public CartItemDTO updateCartItem(int id, CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        if(cartItemDTO.getCart() != null) {
            cartItem.setCart(modelMapper.map(cartItemDTO.getCart(), Cart.class));
        }

        if(cartItemDTO.getProduct() != null) {
            cartItem.setProduct(modelMapper.map(cartItemDTO.getProduct(), Product.class));
        }

        if(cartItemDTO.getQuantity() != 0) {
            cartItem.setQuantity(cartItemDTO.getQuantity());
        }



        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return convertToDTO(updatedCartItem);
    }

    public void deleteCartItem(int id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found with id: " + id));

        Cart cart = cartItem.getCart();
        if (cart != null) {
            cart.getCartItems().remove(cartItem);
            cartRepository.save(cart);
        } else {
            cartItemRepository.delete(cartItem);
        }
    }
}