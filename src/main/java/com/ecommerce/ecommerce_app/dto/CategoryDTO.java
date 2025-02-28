package com.ecommerce.ecommerce_app.dto;

import com.ecommerce.ecommerce_app.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("product")
public class CategoryDTO {
    private int id;
    private String name;
    private String description;
    private List<ProductDTO> product;
}
