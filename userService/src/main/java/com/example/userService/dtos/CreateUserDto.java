package com.example.userService.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {

    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private double creditBalance;
}