package com.example.userService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "phone")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @NonNull
    @Email(regexp = ".+@.+\\..+")
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String address;

    @NonNull
    @PositiveOrZero
    @Column(name = "credit_balance", nullable = false)
    private double creditBalance;

    @NonNull
    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    @Column(nullable = false, unique = true)
    private String phone;


    private String resetToken;

    private Date resetTokenExpiry;
}
