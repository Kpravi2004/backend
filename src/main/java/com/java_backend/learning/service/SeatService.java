package com.java_backend.learning.service;

import com.java_backend.learning.entity.SeatMaster;
import com.java_backend.learning.entity.TableMaster;
import com.java_backend.learning.repository.SeatRepository;
import com.java_backend.learning.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepo;
    private final TableRepository tableRepo;

    public SeatService(SeatRepository seatRepo, TableRepository tableRepo) {
        this.seatRepo = seatRepo;
        this.tableRepo = tableRepo;
    }

    // ✅ CREATE
    public SeatMaster create(Integer tableId, SeatMaster seat) {

        TableMaster table = tableRepo.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        seat.setTable(table);

        if (seat.getStatus() == null)
            seat.setStatus("Free");

        return seatRepo.save(seat);
    }

    // ✅ GET ALL
    public List<SeatMaster> getAll() {
        return seatRepo.findAll();
    }

    // ✅ GET BY ID
    public SeatMaster getById(Integer id) {
        return seatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    // ✅ GET BY TABLE
    public List<SeatMaster> getByTable(Integer tableId) {
        return seatRepo.findByTable_TableId(tableId);
    }

    // ✅ FULL UPDATE
    public SeatMaster update(Integer id, SeatMaster updated) {

        SeatMaster seat = getById(id);

        seat.setSeatNo(updated.getSeatNo());
        seat.setStatus(updated.getStatus());

        return seatRepo.save(seat);
    }

    // ✅ PARTIAL UPDATE
    public SeatMaster patch(Integer id, SeatMaster updated) {

        SeatMaster seat = getById(id);

        if (updated.getSeatNo() != null)
            seat.setSeatNo(updated.getSeatNo());

        if (updated.getStatus() != null)
            seat.setStatus(updated.getStatus());

        return seatRepo.save(seat);
    }

    // ✅ DELETE
    public void delete(Integer id) {
        seatRepo.deleteById(id);
    }
}