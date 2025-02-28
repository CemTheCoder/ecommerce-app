package com.ecommerce.ecommerce_app.service;
import com.ecommerce.ecommerce_app.entity.User;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import com.ecommerce.ecommerce_app.security.JwtUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class); // Logger ekleyin

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // JwtUserDetails oluştur
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(int id ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        return JwtUserDetails.create(user);
    }
}