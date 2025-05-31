package com.example.orderService.repo;

import com.example.orderService.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Page<Order> findAllOrders(Pageable pageable);

    Page<Order> findByUserId(int userId, Pageable pageable);

    Optional<Order> findByOrderId(int orderId);

    Optional<Order> findByOrderIdAndUserId(int orderId, int userId);
}
