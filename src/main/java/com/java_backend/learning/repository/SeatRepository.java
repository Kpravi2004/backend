package com.java_backend.learning.repository;

import com.java_backend.learning.entity.SeatMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatMaster, Integer> {

    List<SeatMaster> findByTable_TableId(Integer tableId);
}