package com.java_backend.learning.repository;

import com.java_backend.learning.entity.TableMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableMaster, Integer> {
}