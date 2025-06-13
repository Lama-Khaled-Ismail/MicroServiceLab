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
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId;

    @NonNull
    @Email(regexp = ".+@.+\\..+")
    @Column(nullable = false, unique = true)
    private String email;



    @NonNull
    @Column(nullable = false)
    private String name;



    @NonNull
    @PositiveOrZero
    @Column(nullable = false)
    private double balance;

}
