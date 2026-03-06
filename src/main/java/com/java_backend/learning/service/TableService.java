package com.java_backend.learning.service;

import com.java_backend.learning.entity.SeatMaster;
import com.java_backend.learning.entity.TableMaster;
import com.java_backend.learning.entity.Waiter;
import com.java_backend.learning.repository.TableRepository;
import com.java_backend.learning.repository.WaiterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TableService {
    private final TableRepository tableRepository;
    private final WaiterRepository waiterRepository;

public TableService(TableRepository tableRepository, WaiterRepository waiterRepository) {
    this.tableRepository = tableRepository;
    this.waiterRepository = waiterRepository;
}

    // ✅ CREATE
    @Transactional
    public TableMaster create(TableMaster table) {
        if (table.getWaiterId() != null) {
            Waiter waiter = waiterRepository.findById(table.getWaiterId())
                    .orElseThrow(() -> new RuntimeException("Waiter not found"));
            table.setWaiter(waiter);
        } else {
            table.setWaiter(null);
        }
        // Ensure seats are linked
        if (table.getSeats() != null) {
            for (SeatMaster seat : table.getSeats()) {
                seat.setTable(table);
            }
        }
        return tableRepository.save(table);
    }

    // ✅ GET ALL
    public List<TableMaster> getAll() {
        return tableRepository.findAll();
    }

    // ✅ GET BY ID
    public TableMaster getById(Integer id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
    }

    // ✅ FULL UPDATE (PUT) – replaces everything including seats
@Transactional
public TableMaster update(Integer id, TableMaster updated) {
    TableMaster table = getById(id);

    // Update basic fields
    table.setTableNumber(updated.getTableNumber());
    table.setTableName(updated.getTableName());
    table.setTotalSeats(updated.getTotalSeats());
    table.setStatus(updated.getStatus());
    table.setFloorName(updated.getFloorName());

    // Log the incoming waiterId
    System.out.println("Incoming waiterId from JSON: " + updated.getWaiterId());

    // Set waiter if provided
    if (updated.getWaiterId() != null) {
        Waiter waiter = waiterRepository.findById(updated.getWaiterId())
                .orElseThrow(() -> new RuntimeException("Waiter not found with id: " + updated.getWaiterId()));
        table.setWaiter(waiter);
        System.out.println("Waiter set: " + waiter.getWaiterName());
    } else {
        // If null, leave existing waiter unchanged
        System.out.println("No waiterId provided, keeping existing waiter: " +
                (table.getWaiter() != null ? table.getWaiter().getWaiterName() : "none"));
    }

    // Update seats (as before)
    if (updated.getSeats() != null) {
        Map<Integer, SeatMaster> incomingSeatMap = updated.getSeats().stream()
                .filter(s -> s.getSeatId() != null && s.getSeatId() > 0)
                .collect(Collectors.toMap(SeatMaster::getSeatId, s -> s));

        for (SeatMaster existing : table.getSeats()) {
            if (incomingSeatMap.containsKey(existing.getSeatId())) {
                SeatMaster incoming = incomingSeatMap.get(existing.getSeatId());
                existing.setSeatNo(incoming.getSeatNo());
                existing.setStatus(incoming.getStatus());
            }
        }

        for (SeatMaster incoming : updated.getSeats()) {
            if (incoming.getSeatId() == null || incoming.getSeatId() == 0) {
                incoming.setSeatId(null);
                incoming.setTable(table);
                table.getSeats().add(incoming);
            }
        }
    }

    TableMaster saved = tableRepository.save(table);
    System.out.println("After save, waiter in DB: " +
            (saved.getWaiter() != null ? saved.getWaiter().getWaiterId() : "null"));
    return saved;
}

    // ✅ PARTIAL UPDATE (PATCH) – only updates provided fields, does not change seats
    @Transactional
    public TableMaster patch(Integer id, TableMaster updated) {
        TableMaster table = getById(id);

        if (updated.getTableNumber() != null)
            table.setTableNumber(updated.getTableNumber());

        if (updated.getTableName() != null)
            table.setTableName(updated.getTableName());

        if (updated.getTotalSeats() != null)
            table.setTotalSeats(updated.getTotalSeats());

        if (updated.getStatus() != null)
            table.setStatus(updated.getStatus());

        if (updated.getFloorName() != null)
            table.setFloorName(updated.getFloorName());

        if (updated.getWaiterId() != null) {
            Waiter waiter = waiterRepository.findById(updated.getWaiterId())
                    .orElseThrow(() -> new RuntimeException("Waiter not found"));
            table.setWaiter(waiter);
        } else if (updated.getWaiterId() == null) {
            // explicitly remove waiter
            table.setWaiter(null);
        }

        // Seats are not updated in PATCH – kept as is
        return tableRepository.save(table);
    }

    // ✅ DELETE
    @Transactional
    public void delete(Integer id) {
        tableRepository.deleteById(id);
    }
}