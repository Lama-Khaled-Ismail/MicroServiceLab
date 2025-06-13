package com.example.productService.service;

import com.example.productService.dtos.CreateProductDto;
import com.example.productService.dtos.ProductDto;
import com.example.productService.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public ProductDto createProduct(CreateProductDto createProductDto);
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(Integer id);
    public ProductDto updateProduct(Integer id, CreateProductDto updateProductDto);
    public void deleteProduct(Integer id);
    public ProductDto updateStock(Integer productId, Integer quantity);
}
