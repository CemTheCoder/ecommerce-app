package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.CreateUserDTO;
import com.ecommerce.ecommerce_app.dto.UpdateUserDTO;
import com.ecommerce.ecommerce_app.dto.UserDTO;
import com.ecommerce.ecommerce_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping("/user")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user/{id}")
    public UserDTO updateUser(@PathVariable int id, @RequestBody UpdateUserDTO updateUserDTO) {
        return userService.updateUser(id, updateUserDTO);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}