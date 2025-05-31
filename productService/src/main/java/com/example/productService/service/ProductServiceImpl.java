package com.example.productService.service;

import com.example.productService.dtos.ProductDTO;
import com.example.productService.entities.Product;
import com.example.productService.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;




    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;

    }

    @Override
    public Product createProduct(ProductDTO dto) {

        Product product = toEntity(dto);
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(ProductDTO dto) {
        Optional<Product> existing = productRepo.findById(dto.getProductId());
        if (existing.isEmpty()) throw new IllegalArgumentException("Product not found");

        Product updated = toEntity(dto);
        return productRepo.save(updated);
    }

    @Override
    public boolean deleteProductById(int id) {
        if (!productRepo.existsById(id)) return false;
        productRepo.deleteById(id);
        return true;
    }

    @Override
    public Product getProductById(int id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public boolean productExists(int productId) {
        return productRepo.existsById(productId);
    }

    @Override
    public Page<Product> filterProducts(String name, String category, Double minPrice, Double maxPrice, String sortDir, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        if ("desc".equalsIgnoreCase(sortDir)) {
            pageable = PageRequest.of(page, size, Sort.by("price").descending());
        }

        return productRepo.findFiltered(name, category, minPrice, maxPrice, pageable);
    }

    // --- Helper methods ---

    private Product toEntity(ProductDTO dto) {
        Product p = new Product();
        p.setProductId(dto.getProductId());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setCategoryId(dto.getCategoryId());
        p.setImage(dto.getImage());
        p.setStock(dto.getStock());
        return p;
    }


}
