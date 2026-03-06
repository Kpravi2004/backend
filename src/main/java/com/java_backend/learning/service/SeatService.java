package com.java_backend.learning.service;

import com.java_backend.learning.entity.SeatMaster;
import com.java_backend.learning.entity.TableMaster;
import com.java_backend.learning.repository.SeatRepository;
import com.java_backend.learning.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final TableRepository tableRepository;

    public SeatService(SeatRepository seatRepository, TableRepository tableRepository) {
        this.seatRepository = seatRepository;
        this.tableRepository = tableRepository;
    }

    // ✅ CREATE (with tableId)
    public SeatMaster create(Integer tableId, SeatMaster seat) {
        TableMaster table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        seat.setTable(table);
        if (seat.getStatus() == null) {
            seat.setStatus("Free");
        }
        if (seat.getBillingStatus() == null) {
            seat.setBillingStatus(false);
        }
        return seatRepository.save(seat);
    }

    // ✅ GET ALL
    public List<SeatMaster> getAll() {
        return seatRepository.findAll();
    }

    // ✅ GET BY ID
    public SeatMaster getById(Integer id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    // ✅ GET BY TABLE
    public List<SeatMaster> getByTable(Integer tableId) {
        return seatRepository.findByTableId(tableId);
    }

    // ✅ FULL UPDATE (PUT)
    public SeatMaster update(Integer id, SeatMaster updated) {
        SeatMaster seat = getById(id);

        seat.setSeatNo(updated.getSeatNo());
        seat.setStatus(updated.getStatus());
        // update billing status if provided
        if (updated.getBillingStatus() != null) {
            seat.setBillingStatus(updated.getBillingStatus());
        }

        // Color is set automatically via setStatus logic in entity
        return seatRepository.save(seat);
    }

    // ✅ PARTIAL UPDATE (PATCH)
    public SeatMaster patch(Integer id, SeatMaster updated) {
        SeatMaster seat = getById(id);

        if (updated.getSeatNo() != null)
            seat.setSeatNo(updated.getSeatNo());

        if (updated.getStatus() != null)
            seat.setStatus(updated.getStatus());

        if (updated.getBillingStatus() != null)
            seat.setBillingStatus(updated.getBillingStatus());

        return seatRepository.save(seat);
    }
    public List<SeatMaster> updateBillingStatus(List<Integer> seatIds, Boolean billingStatus) {
    List<SeatMaster> seats = seatRepository.findAllById(seatIds);
    for (SeatMaster seat : seats) {
        seat.setBillingStatus(billingStatus);
    }
    return seatRepository.saveAll(seats);
}

    // ✅ DELETE
    public void delete(Integer id) {
        seatRepository.deleteById(id);
    }

public void updateBillingStatus(Integer seatId, boolean billingStatus) {
    SeatMaster seat = getById(seatId);
    seat.setBillingStatus(billingStatus);
    seatRepository.save(seat);
}
}