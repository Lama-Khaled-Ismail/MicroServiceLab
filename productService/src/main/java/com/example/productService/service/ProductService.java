package com.example.productService.service;

import com.example.productService.dtos.ProductDTO;
import com.example.productService.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO product);
    Product updateProduct(ProductDTO product);
    boolean deleteProductById(int id);
    Product getProductById(int id);
    List<Product> getAllProducts();
    boolean productExists(int productId);
    public Page<Product> filterProducts(String name, String category, Double minPrice, Double maxPrice, String sortDir, int page, int size);
}
