package com.example.orderService.mappers;


import com.example.orderService.dtos.CreateOrderDto;
import com.example.orderService.dtos.OrderDto;
import com.example.orderService.dtos.OrderItemDto;
import com.example.orderService.entities.Order;
import com.example.orderService.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {


    OrderDto toDTO(Order order);


    Order toEntity(OrderDto orderDto);

    @Mapping(target = "orderId", ignore = true)
    Order createDtoToEntity(CreateOrderDto createOrderDto);

    CreateOrderDto toCreateDto(Order order);

    // Order Item mappings
    OrderItemDto orderItemToDto(OrderItem orderItem);

    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem orderItemDtoToEntity(OrderItemDto orderItemDto);

    List<OrderItemDto> orderItemsToDto(List<OrderItem> orderItems);

    List<OrderItem> orderItemDtosToEntity(List<OrderItemDto> orderItemDtos);
}
