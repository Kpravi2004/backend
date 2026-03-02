package com.java_backend.learning.controller;

import com.java_backend.learning.entity.SeatMaster;
import com.java_backend.learning.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@CrossOrigin("*")
public class SeatController {

    private final SeatService service;

    public SeatController(SeatService service) {
        this.service = service;
    }

    @PostMapping("/table/{tableId}")
    public SeatMaster create(@PathVariable Integer tableId,
                             @RequestBody SeatMaster seat) {
        return service.create(tableId, seat);
    }

    @GetMapping
    public List<SeatMaster> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SeatMaster getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/table/{tableId}")
    public List<SeatMaster> getByTable(@PathVariable Integer tableId) {
        return service.getByTable(tableId);
    }

    @PutMapping("/{id}")
    public SeatMaster update(@PathVariable Integer id,
                             @RequestBody SeatMaster seat) {
        return service.update(id, seat);
    }

    @PatchMapping("/{id}")
    public SeatMaster patch(@PathVariable Integer id,
                            @RequestBody SeatMaster seat) {
        return service.patch(id, seat);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Seat deleted successfully";
    }
}