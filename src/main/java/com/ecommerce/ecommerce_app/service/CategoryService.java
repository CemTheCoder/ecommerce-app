package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.*;
import com.ecommerce.ecommerce_app.entity.Category;
import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public Category convertToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }


    public CategoryDTO getCategory(int id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: "+id));
        return convertToDTO(category);

    }

    public List<CategoryDTO> getCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        if(categoryDTO.getProduct() != null) {
            Type listType = new TypeToken<List<Product>>() {}.getType();
            category.setProduct(modelMapper.map(categoryDTO.getProduct(), listType));
        }
        Category savedCategory = this.categoryRepository.save(category);

        return convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(int id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));


        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }

        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }

        if (categoryDTO.getProduct() != null) {
            Type listType = new TypeToken<List<Product>>() {}.getType();
            category.setProduct(modelMapper.map(categoryDTO.getProduct(), listType));
        }


        Category updatedCategory = categoryRepository.save(category);

        return convertToDTO(updatedCategory);
    }


    public void deleteCategory(int id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: "+ id));
        this.categoryRepository.delete(category);
    }



}
