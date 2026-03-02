package com.java_backend.learning.controller;

import com.java_backend.learning.entity.Waiter;
import com.java_backend.learning.service.WaiterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waiters")
@CrossOrigin("*")
public class WaiterController {

    private final WaiterService service;

    public WaiterController(WaiterService service) {
        this.service = service;
    }

    @PostMapping
    public Waiter create(@RequestBody Waiter waiter) {
        return service.create(waiter);
    }

    @GetMapping
    public List<Waiter> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Waiter getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Waiter update(@PathVariable Integer id,
                         @RequestBody Waiter waiter) {
        return service.update(id, waiter);
    }

    @PatchMapping("/{id}")
    public Waiter patch(@PathVariable Integer id,
                        @RequestBody Waiter waiter) {
        return service.patch(id, waiter);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Waiter deleted successfully";
    }
}