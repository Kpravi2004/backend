package com.java_backend.learning.repository;

import com.java_backend.learning.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM BillItem bi WHERE bi.bill.id = :billId")
    void deleteByBillId(@Param("billId") Long billId);
}