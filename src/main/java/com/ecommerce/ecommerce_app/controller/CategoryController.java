package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.CategoryDTO;
import com.ecommerce.ecommerce_app.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/{id}")
    public CategoryDTO getCategory(@PathVariable int id) {
        return this.categoryService.getCategory(id);
    }

    @GetMapping("/category")
    public List<CategoryDTO> getCategories() {
        return this.categoryService.getCategories();
    }

    @PostMapping("/category")
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return this.categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/category/{id}")
    public CategoryDTO updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        return this.categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable int id) {
        this.categoryService.deleteCategory(id);
    }
}
