package com.example.orderService.mappers;


import com.example.orderService.dtos.OrderDto;
import com.example.orderService.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {


    OrderDto toDto(Order order);


    Order toEntity(OrderDto orderDto);
}
