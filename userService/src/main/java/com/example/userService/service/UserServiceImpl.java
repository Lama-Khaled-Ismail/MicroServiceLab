package com.example.userService.service;

import com.example.userService.dtos.CreateUserDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import com.example.userService.mappers.UserMapper;
import com.example.userService.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByname(createUserDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.createDtoToEntity(createUserDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    public UserDto updateBalance(Integer userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBalance() + amount < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() + amount);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }


}