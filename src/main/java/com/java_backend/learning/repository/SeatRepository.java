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

    @Modifying
    @Transactional
    @Query("UPDATE SeatMaster s SET s.billingStatus = :billingStatus WHERE s.id = :seatId")
    int updateBillingStatus(@Param("seatId") int seatId, @Param("billingStatus") boolean billingStatus);

    @Query("SELECT s FROM SeatMaster s WHERE s.table.id = :tableId")
    List<SeatMaster> findByTableId(@Param("tableId") Integer tableId);

    @Modifying
    @Transactional
    @Query("UPDATE SeatMaster s SET s.status = :status, s.billingStatus = :billingStatus WHERE s.id = :seatId")
    int updateSeatStatusAndBilling(@Param("seatId") int seatId, 
                                    @Param("status") String status, 
                                    @Param("billingStatus") boolean billingStatus);

    @Query("SELECT s FROM SeatMaster s LEFT JOIN FETCH s.table t LEFT JOIN FETCH t.waiter WHERE s.id IN :seatIds")
    List<SeatMaster> findSeatsWithTableAndWaiter(@Param("seatIds") List<Integer> seatIds);
}