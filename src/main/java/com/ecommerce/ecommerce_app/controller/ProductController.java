package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.ProductDTO;
import com.ecommerce.ecommerce_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProduct(@PathVariable int id) {
        return this.productService.getProduct(id);
    }

    @GetMapping("/product")
    public List<ProductDTO> getProduct() {
        return this.productService.getProducts();
    }

    @PostMapping("/product")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return this.productService.createProduct(productDTO);
    }

    @PutMapping("/product/{id}")
    public ProductDTO updateProduct(@PathVariable int id,@RequestBody ProductDTO productDTO) {
        return this.productService.updateProduct(id,productDTO);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        this.productService.deleteProduct(id);
    }

}
