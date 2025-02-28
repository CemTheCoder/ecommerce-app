package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.ProductDTO;
import com.ecommerce.ecommerce_app.entity.Category;
import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.repository.CategoryRepository;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }


    public ProductDTO getProduct(int id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    public List<ProductDTO> getProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(productDTO.getQuantityInStock());
        product.setRating(0);
        product.setCategory(modelMapper.map(productDTO.getCategory(), Category.class));


        Product savedProduct = this.productRepository.save(product);

        return convertToDTO(savedProduct);
    }


    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));


        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != 0) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getQuantityInStock() != 0) {
            product.setQuantityInStock(productDTO.getQuantityInStock());
        }
        if (productDTO.getRating() != 0) {
            product.setRating(productDTO.getRating());
        }
        if (productDTO.getCategory() != null) {
            product.setCategory(modelMapper.map(productDTO.getCategory(), Category.class));
        }

        Product updatedProduct = productRepository.save(product);

        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(int id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: "+ id));
        this.productRepository.delete(product);
    }

}
