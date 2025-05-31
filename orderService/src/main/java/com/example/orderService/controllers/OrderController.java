package com.example.orderService.controllers;


import com.example.orderService.Exceptions.OrderNotFoundException;
import com.example.orderService.dtos.OrderDto;
import com.example.orderService.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(OrderDto orderDto) {

        orderService.checkout(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getOrders(
            @PageableDefault(size = 10, sort = "orderId", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<OrderDto> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(
            @PathVariable int userId,
            @PageableDefault(size = 10, sort = "orderId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderDto> orders = orderService.getOrdersByUserId(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
        Optional<OrderDto> orderDto = orderService.getOrderById(orderId);
        return orderDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/{orderId}")
    public ResponseEntity<OrderDto> getOrderForUser(
            @PathVariable int userId,
            @PathVariable int orderId) {

        Optional<OrderDto> orderDto = orderService.getOrderForUser(userId, orderId);
        return orderDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable int orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order cancelled successfully.");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }








}
