package com.java_backend.learning.controller;

import com.java_backend.learning.entity.SeatMaster;
import com.java_backend.learning.service.SeatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seats")
@CrossOrigin("*")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Create a new seat for a specific table
    @PostMapping("/table/{tableId}")
    public SeatMaster create(@PathVariable Integer tableId,
                             @RequestBody SeatMaster seat) {
        return seatService.create(tableId, seat);
    }

    // Get all seats
    @GetMapping
    public List<SeatMaster> getAll() {
        return seatService.getAll();
    }

    // Get seat by ID
    @GetMapping("/{id}")
    public SeatMaster getById(@PathVariable Integer id) {
        return seatService.getById(id);
    }

    // Get all seats for a specific table
    @GetMapping("/table/{tableId}")
    public List<SeatMaster> getByTable(@PathVariable Integer tableId) {
        return seatService.getByTable(tableId);
    }

    // Full update of a seat (PUT)
    @PutMapping("/{id}")
    public SeatMaster update(@PathVariable Integer id,
                             @RequestBody SeatMaster seat) {
        return seatService.update(id, seat);
    }

    // Partial update of a seat (PATCH)
    @PatchMapping("/{id}")
    public SeatMaster patch(@PathVariable Integer id,
                            @RequestBody SeatMaster seat) {
        return seatService.patch(id, seat);
    }

    // Delete a seat
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        seatService.delete(id);
        return "Seat deleted successfully";
    }

    // Bulk update billing status for multiple seats (used after bill creation)
     @PutMapping("/{seatId}/billing-status")
public ResponseEntity<?> updateBillingStatus(
        @PathVariable Integer seatId,
        @RequestBody Map<String, Boolean> request) {
    Boolean billingStatus = request.get("billing_status");
    if (billingStatus == null) {
        return ResponseEntity.badRequest().body("Missing billing_status field");
    }
    seatService.updateBillingStatus(seatId, billingStatus);
    return ResponseEntity.ok().build();
}
   
}