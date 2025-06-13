package com.example.userService.service;


import com.example.userService.dtos.CreateUserDto;

import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {

    public List<UserDto> getAllUsers();
    public UserDto createUser(CreateUserDto createUserDto);
    public UserDto getUserById(Integer id);
    public UserDto updateBalance(Integer userId, Double amount);

    }
