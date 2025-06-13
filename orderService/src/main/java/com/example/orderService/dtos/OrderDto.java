package com.example.orderService.dtos;


import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer orderId;
    private Integer userId;
    private List<OrderItemDto> orderItems;

}
