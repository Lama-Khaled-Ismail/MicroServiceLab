package com.example.productService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productId;

    @NonNull
    @Column(nullable = false)
    private String name;


    @NonNull
    @Min(0)
    @Column(nullable = false)
    private Double price;


    @NonNull
    @Min(0)
    @Column(nullable = false)
    private Integer stock;

    
}
