package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.CartDTO;
import com.ecommerce.ecommerce_app.entity.Cart;
import com.ecommerce.ecommerce_app.entity.CartItem;
import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.entity.User;
import com.ecommerce.ecommerce_app.repository.CartItemRepository;
import com.ecommerce.ecommerce_app.repository.CartRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ModelMapper modelMapper,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cartItemRepository= cartItemRepository;
    }


    public Cart convertToEntity(CartDTO cartDTO) {
        return this.modelMapper.map(cartDTO , Cart.class);
    }

    public CartDTO convertToDTO(Cart cart) {
        return this.modelMapper.map(cart, CartDTO.class);
    }



    public CartDTO getCart(int id) {
        Cart cart = this.cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
        return convertToDTO(cart);
    }

    public List<CartDTO> getCarts() {
        return this.cartRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setUser(modelMapper.map(cartDTO.getUser(), User.class));
        if(cartDTO.getCartItems() != null) {
            Type listType = new TypeToken<List<CartItem>>() {}.getType();
            cart.setCartItems(modelMapper.map(cartDTO.getCartItems(), listType));
        }
        cart.setTotalPrice(cartDTO.getTotalPrice());
        Cart savedCart = this.cartRepository.save(cart);
        return convertToDTO(savedCart);
    }

    public CartDTO updateCart(int id, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));

        if (cartDTO.getUser() != null) {
            cart.setUser(modelMapper.map(cartDTO.getUser(), User.class));
        }

        if (cartDTO.getTotalPrice() != null) {
            cart.setTotalPrice(cartDTO.getTotalPrice());
        }

        if (cartDTO.getCartItems() != null) {
            Type listType = new TypeToken<List<CartItem>>() {}.getType();
            cart.setCartItems(modelMapper.map(cartDTO.getCartItems(), listType));        }

        Cart updatedCart = cartRepository.save(cart);
        return convertToDTO(updatedCart);
    }


    public void deleteCart(int cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart bulunamadı!"));

        if(cart.getUser() != null){
            User user = cart.getUser();
            user.setCart(null);
            userRepository.save(user);
        }

        cart.getCartItems().clear();

        cartRepository.delete(cart);
    }
}