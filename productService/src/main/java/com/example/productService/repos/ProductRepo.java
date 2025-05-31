package com.example.productService.repos;

import com.example.productService.entities.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:category IS NULL OR CAST(p.categoryId AS string) = :category) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findFiltered(@Param("name") String name,
                               @Param("category") String category,
                               @Param("minPrice") Double minPrice,
                               @Param("maxPrice") Double maxPrice,
                               Pageable pageable);
}
