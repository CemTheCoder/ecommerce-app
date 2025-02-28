package com.ecommerce.ecommerce_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String message;

    private int userId;

    private int roleId;

}
