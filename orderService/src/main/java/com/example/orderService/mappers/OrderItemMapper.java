package com.example.orderService.mappers;


import com.example.orderService.dtos.OrderItemDto;
import com.example.orderService.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    OrderItemDto toDTO(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDto orderItemDto);

    List<OrderItemDto> toDTOList(List<OrderItem> orderItems);

    List<OrderItem> toEntityList(List<OrderItemDto> orderItemDtos);

    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem createDtoToEntity(OrderItemDto orderItemDto);
}