package com.example.orderService.service;

import com.example.orderService.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductServiceClient {

    @Value("${product.service.url}")
    private String productServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ProductDto getProductById(Integer productId) {
        try {
            String url = productServiceUrl + "/" + productId;
            return restTemplate.getForObject(url, ProductDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Product service unavailable");
        }
    }

    public void updateProductStock(Integer productId, Integer quantity) {
        try {
            String url = productServiceUrl + "/" + productId + "/stock?quantity=" + quantity;
            restTemplate.put(url, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product stock");
        }
    }
}
