package com.ms.productservice.repository;


import com.ms.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    @Query("SELECT p from Product p  where p.active = true AND p.stockQuantity>0 " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Product> findProductsByKeyword(String keyword);

    boolean existsByName(String productName);
}
