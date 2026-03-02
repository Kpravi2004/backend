package com.java_backend.learning.repository;

import com.java_backend.learning.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByCode(String code);
    void deleteByCode(String code);
}