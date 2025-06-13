package com.example.productService.mappers;

import com.example.productService.dtos.CreateProductDto;
import com.example.productService.dtos.ProductDto;
import com.example.productService.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDto toDTO(Product product);

    Product toEntity(ProductDto productDto);

    @Mapping(target = "productId", ignore = true)
    Product createDtoToEntity(CreateProductDto createProductDto);

    CreateProductDto toCreateDto(Product product);

}