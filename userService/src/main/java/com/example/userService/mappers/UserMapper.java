package com.example.userService.mappers;


import com.example.userService.dtos.CreateUserDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert User entity to UserDto (excludes password for security)
     */

    UserDto toDTO(User user);

    /**
     * Convert UserDto to User entity (for updates)
     */
    @Mapping(target = "password", ignore = true) 
    @Mapping(target = "cart", ignore = true)     // Ignore relationships
    @Mapping(target = "orders", ignore = true)   // Ignore relationships
    User toEntity(UserDto dto);

    /**
     * Convert CreateUserDto to User entity (for new user creation)
     */
    @Mapping(target = "userId", ignore = true)   // Auto-generated
    @Mapping(target = "cart", ignore = true)     // Will be created separately
    @Mapping(target = "orders", ignore = true)   // Will be created separately
    User createDtoToEntity(CreateUserDto createUserDto);
}