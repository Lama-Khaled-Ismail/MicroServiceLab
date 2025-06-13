package com.example.userService.mappers;


import com.example.userService.dtos.CreateUserDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {



    UserDto toDTO(User user);



    User toEntity(UserDto dto);


    @Mapping(target = "userId", ignore = true)   // Auto-generated
    User createDtoToEntity(CreateUserDto createUserDto);
}