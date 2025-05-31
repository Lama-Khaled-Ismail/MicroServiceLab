package com.example.orderService.service;

import com.example.orderService.Exceptions.OrderNotFoundException;
import com.example.orderService.dtos.OrderDto;
import com.example.orderService.dtos.OrderItemDto;
import com.example.orderService.entities.Order;
import com.example.orderService.entities.OrderStatus;
import com.example.orderService.mappers.OrderMapper;
import com.example.orderService.repo.OrderRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepository;

    private final OrderMapper orderMapper;

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${product.service.url}")
    private String productServiceUrl;



    public OrderServiceImpl(OrderRepo orderRepository,
                            OrderMapper orderMapper, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;

    }


    @Transactional
    public Order checkout(  OrderDto orderDto) {
        // remote call to check user exits
        String userUrl = userServiceUrl + "/" + orderDto.getUserId();
        Boolean userExists = restTemplate.getForObject(userUrl, Boolean.class);
        if (userExists == null || !userExists) {
            throw new IllegalArgumentException("User does not exist with ID: " + orderDto.getUserId());
        }
        // remote call to check product exitsOrderController
        for (OrderItemDto item : orderDto.getItems()) {
            String productUrl = productServiceUrl + "/" + item.getProductId();
            Boolean productExists = restTemplate.getForObject(productUrl, Boolean.class);
            if (productExists == null || !productExists) {
                throw new IllegalArgumentException("Product does not exist: " + item.getProductName());
            }
        }
        Order order = orderMapper.toEntity(orderDto);
        return orderRepository.save(order);
    }

    public Page<OrderDto> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAllOrders(pageable);

        return orderPage.map(orderMapper::toDto);
    }


    public Page<OrderDto> getOrdersByUserId(int userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    public Optional<OrderDto> getOrderById(int orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(orderMapper::toDto);
    }

    public Optional<OrderDto> getOrderForUser(int userId, int orderId) {
        return orderRepository.findByOrderIdAndUserId(orderId, userId)
                .map(orderMapper::toDto);
    }

    @Transactional
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

}
