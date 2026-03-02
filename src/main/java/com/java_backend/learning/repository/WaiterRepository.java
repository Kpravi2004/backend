package com.java_backend.learning.repository;

import com.java_backend.learning.entity.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaiterRepository extends JpaRepository<Waiter, Integer> {
}