package com.example.productService.service;

import com.example.productService.dtos.CreateProductDto;
import com.example.productService.dtos.ProductDto;
import com.example.productService.entities.Product;
import com.example.productService.mappers.ProductMapper;
import com.example.productService.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;




    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public ProductDto createProduct(CreateProductDto createProductDto) {
        Product product = productMapper.createDtoToEntity(createProductDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDTO(product);
    }

    public ProductDto updateProduct(Integer id, CreateProductDto updateProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updateProductDto.getName());
        product.setPrice(updateProductDto.getPrice());
        product.setStock(updateProductDto.getStock());


        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public ProductDto updateStock(Integer productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() + quantity < 0) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() + quantity);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }




}
