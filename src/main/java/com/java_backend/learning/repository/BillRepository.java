package com.java_backend.learning.repository;

import com.java_backend.learning.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByStatus(String status);
    List<Bill> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}