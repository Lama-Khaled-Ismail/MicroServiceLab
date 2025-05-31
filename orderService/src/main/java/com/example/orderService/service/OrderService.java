package com.example.orderService.service;

import com.example.orderService.dtos.OrderDto;
import com.example.orderService.entities.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    Order checkout(OrderDto orderDto);
    Page<OrderDto> getAllOrders(Pageable pageable);
    Page<OrderDto> getOrdersByUserId(int userId, Pageable pageable);
    Optional<OrderDto> getOrderById(int orderId);
    Optional<OrderDto> getOrderForUser(int userId, int orderId);
    void cancelOrder(int orderId);
}
