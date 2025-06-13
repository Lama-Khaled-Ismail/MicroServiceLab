package com.example.orderService.service;

import com.example.orderService.dtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {

    @Value("${user.service.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public UserDto getUserById(Integer userId) {
        try {
            String url = userServiceUrl + "/" + userId;
            return restTemplate.getForObject(url, UserDto.class);
        } catch (Exception e) {
            throw new RuntimeException("User service unavailable");
        }
    }

    public void updateUserBalance(Integer userId, Double amount) {
        try {
            String url = userServiceUrl + "/" + userId + "/balance?amount=" + amount;
            restTemplate.put(url, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user balance");
        }
    }
}

