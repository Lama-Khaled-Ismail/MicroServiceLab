package com.example.orderService.service;

import com.example.orderService.dtos.*;
import com.example.orderService.entities.Order;
import com.example.orderService.entities.OrderItem;
import com.example.orderService.mappers.OrderMapper;

import com.example.orderService.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        // Validate user exists
        UserDto user = userServiceClient.getUserById(createOrderDto.getUserId());

        // Calculate total and validate products
        double totalAmount = 0.0;
        for (OrderItemDto itemDto : createOrderDto.getOrderItems()) {
            ProductDto product = productServiceClient.getProductById(itemDto.getProductId());

            if (product.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            itemDto.setItemPrice(product.getPrice());
            totalAmount += product.getPrice() * itemDto.getQuantity();
        }

        // Check user balance
        if (user.getBalance() < totalAmount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Create order
        Order order = new Order();
        order.setUserId(createOrderDto.getUserId());


        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto itemDto : createOrderDto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(itemDto.getProductId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setItemPrice(itemDto.getItemPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }



    public OrderDto getOrderById(Integer id) {
        Order order = (Order) orderRepository.findByOrOrderId(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDTO(order);
    }

    public List<OrderDto> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
