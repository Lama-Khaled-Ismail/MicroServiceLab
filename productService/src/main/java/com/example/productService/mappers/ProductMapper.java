package com.example.productService.mappers;

import com.example.productService.dtos.ProductDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDTO entityToDTO(Product entity);

    Product dtoToEntity(ProductDTO dto);
}
