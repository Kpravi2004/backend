package com.java_backend.learning.service;

import com.java_backend.learning.entity.TableMaster;
import com.java_backend.learning.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final TableRepository repository;

    public TableService(TableRepository repository) {
        this.repository = repository;
    }

    // ✅ CREATE
    public TableMaster create(TableMaster table) {
        return repository.save(table);
    }

    // ✅ GET ALL
    public List<TableMaster> getAll() {
        return repository.findAll();
    }

    // ✅ GET BY ID
    public TableMaster getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
    }

    // ✅ FULL UPDATE (PUT)
    public TableMaster update(Integer id, TableMaster updated) {

        TableMaster table = getById(id);

        table.setTableNumber(updated.getTableNumber());
        table.setTableName(updated.getTableName());
        table.setTotalSeats(updated.getTotalSeats());
        table.setStatus(updated.getStatus());
        table.setFloorName(updated.getFloorName());

        return repository.save(table);
    }

    // ✅ PARTIAL UPDATE (PATCH)
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

        return repository.save(table);
    }

    // ✅ DELETE
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}