package com.example.orderService.dtos;


import com.example.orderService.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private int orderId;
    private int userId;
    private String email;
    private OrderStatus status;
    private List<OrderItemDto> items;
}
