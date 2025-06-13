package com.example.orderService.service;

import com.example.orderService.dtos.CreateOrderDto;
import com.example.orderService.dtos.OrderDto;
import com.example.orderService.entities.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public OrderDto createOrder(CreateOrderDto createOrderDto);
    public OrderDto getOrderById(Integer id);
    public List<OrderDto> getOrdersByUserId(Integer userId);
    public List<OrderDto> getAllOrders();
}
