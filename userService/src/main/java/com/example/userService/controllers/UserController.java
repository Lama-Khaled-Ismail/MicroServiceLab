package com.example.userService.controllers;


import com.example.userService.dtos.CreateUserDto;

import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import com.example.userService.mappers.UserMapper;
import com.example.userService.service.UserService;
import com.example.userService.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService ) {
        this.userService = userService;

    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserDto savedUser = userService.createUser(createUserDto);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto user =  userService.getUserById(id);
        return ResponseEntity.ok(user);
    }






}