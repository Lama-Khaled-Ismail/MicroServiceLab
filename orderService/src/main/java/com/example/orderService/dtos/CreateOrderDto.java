package com.example.orderService.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemDto> orderItems;
}