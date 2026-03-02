package com.java_backend.learning.controller;

import com.java_backend.learning.entity.TableMaster;
import com.java_backend.learning.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@CrossOrigin("*")
public class TableController {

    private final TableService service;

    public TableController(TableService service) {
        this.service = service;
    }

    @PostMapping
    public TableMaster create(@RequestBody TableMaster table) {
        return service.create(table);
    }

    @GetMapping
    public List<TableMaster> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public TableMaster getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public TableMaster update(@PathVariable Integer id,
                              @RequestBody TableMaster table) {
        return service.update(id, table);
    }

    @PatchMapping("/{id}")
    public TableMaster patch(@PathVariable Integer id,
                             @RequestBody TableMaster table) {
        return service.patch(id, table);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Table deleted successfully";
    }
}