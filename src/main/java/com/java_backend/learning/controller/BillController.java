package com.java_backend.learning.controller;

import com.java_backend.learning.dto.BillRequest;
import com.java_backend.learning.dto.BillResponse;
import com.java_backend.learning.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@CrossOrigin(origins = "*")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody BillRequest request) {
        BillResponse response = billService.createBill(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        List<BillResponse> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillResponse>> getBillsByStatus(@PathVariable String status) {
        List<BillResponse> bills = billService.getBillsByStatus(status);
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        BillResponse bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<BillResponse> confirmBill(@PathVariable Long id) {
        BillResponse response = billService.confirmBill(id);
        return ResponseEntity.ok(response);
    }

    // OPTIONS handler remains unchanged
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Max-Age", "3600")
                .build();
    }
}