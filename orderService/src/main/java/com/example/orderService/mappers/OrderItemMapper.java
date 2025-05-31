package com.example.orderService.mappers;


import com.example.orderService.dtos.OrderItemDto;
import com.example.orderService.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {





    OrderItemDto toDto(OrderItem orderItem);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDto orderItemDto);
}