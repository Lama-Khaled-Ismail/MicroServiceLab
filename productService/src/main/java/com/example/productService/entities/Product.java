package com.example.productService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
// Remove @Data
@ToString(exclude = {"orderItems"})
@EqualsAndHashCode(exclude = {"orderItems"})
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    private String description;

    @NonNull
    @Min(0)
    @Column(name = "price", nullable = false)
    private double price;


    private int categoryId;


    @NonNull
    @Column(nullable = false)
    private String image;

    @NonNull
    @Min(0)
    @Column(name = "stock", nullable = false)
    private int stock;

    // @NonNull
    @Transient
     private String status;

//    @OneToMany(mappedBy = "product")
//    private List<OrderItem> orderItems = new ArrayList<>();
    @Transient
    public String getStatus() {
        if(this.stock == 0)
            return "Out of Stock";
        return "In Stock";
    }

    @Transient
    public void setStatus() {
        this.stock = 0;
    }

    
}
