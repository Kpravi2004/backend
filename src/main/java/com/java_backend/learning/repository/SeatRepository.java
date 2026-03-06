package com.java_backend.learning.repository;

import com.java_backend.learning.entity.SeatMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatMaster, Integer> {

    // Update billing status of a seat
    @Modifying
    @Transactional
    @Query("UPDATE SeatMaster s SET s.billingStatus = :billingStatus WHERE s.id = :seatId")
    int updateBillingStatus(@Param("seatId") int seatId, @Param("billingStatus") boolean billingStatus);

    // Find seats by the associated table's ID
    @Query("SELECT s FROM SeatMaster s WHERE s.table.id = :tableId")
    List<SeatMaster> findByTableId(@Param("tableId") Integer tableId);

    // New: Update both status and billing status (used when confirming a bill)
    @Modifying
    @Transactional
    @Query("UPDATE SeatMaster s SET s.status = :status, s.billingStatus = :billingStatus WHERE s.id = :seatId")
    int updateSeatStatusAndBilling(@Param("seatId") int seatId, 
                                    @Param("status") String status, 
                                    @Param("billingStatus") boolean billingStatus);
}