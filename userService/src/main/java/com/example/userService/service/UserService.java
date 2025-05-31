package com.example.userService.service;


import com.example.userService.dtos.LoginRequestDto;
import com.example.userService.dtos.UserDto;
import com.example.userService.entities.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {

    public User getUserByEmail(String email);

    public User getUserById(int userId) ;

    public List<User> getAllUsers();

    public User authenticateUser(String email, String password);

    User createUser(User user);

    public void forgotPassword(String email);
    public void resetPassword(String resetToken, String newPassword);

   public int changeBalance(int userId, double amount) ;

   public void verifyEmail(String verificationToken);
   public boolean emailExists(String email);

    // public User updateUserProfile(int userId, UserProfileDto profileDto) ;
    //public void changePassword(int userId, ChangePasswordDto changePasswordDto);


    /************************ AuthService (login , logout) ***************/
    UserDto login(LoginRequestDto loginRequestDto, HttpSession session);
    void logout(HttpSession session);

    }
