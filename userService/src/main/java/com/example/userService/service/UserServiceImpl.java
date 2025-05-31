package com.example.userService.service;

import com.example.userService.dtos.LoginRequestDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import com.example.userService.repos.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        // Save password as-is (INSECURE: for development only)
        return userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = getUserByEmail(email);
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 3600_000)); // 1 hour
        userRepository.save(user);

        System.out.println("Reset token: " + resetToken);
    }

    @Override
    public void resetPassword(String resetToken, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(resetToken);
        if (userOpt.isEmpty()) throw new IllegalArgumentException("Invalid reset token");

        User user = userOpt.get();
        if (user.getResetTokenExpiry().before(new Date())) {
            throw new IllegalStateException("Reset token expired");
        }

        user.setPassword(newPassword); // Save plain text password (INSECURE)
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    public int changeBalance(int userId, double amount) {
        User user = getUserById(userId);
        double newBalance = user.getCreditBalance() + amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        user.setCreditBalance(newBalance);
        userRepository.save(user);
        return (int) user.getCreditBalance();
    }

    @Override
    public void verifyEmail(String verificationToken) {
        Optional<User> userOpt = userRepository.findByResetToken(verificationToken);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }

        User user = userOpt.get();
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto login(LoginRequestDto loginRequestDto, HttpSession session) {
        User user = authenticateUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        session.setAttribute("userId", user.getUserId());

        try {
            String url = orderServiceUrl + "/orders/count/" + user.getUserId();
            Integer orderCount = restTemplate.getForObject(url, Integer.class);
            System.out.println("User has " + orderCount + " orders.");
        } catch (Exception e) {
            System.err.println("Order service unavailable: " + e.getMessage());
        }

        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getAddress(),
                user.getPhone(),
                user.getCreditBalance()

        );
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
