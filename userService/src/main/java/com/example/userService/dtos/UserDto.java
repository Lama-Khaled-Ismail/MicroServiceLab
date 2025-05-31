package com.example.userService.dtos;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int userId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private double creditBalance;

}