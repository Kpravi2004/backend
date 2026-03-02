package com.java_backend.learning.controller;

import com.java_backend.learning.entity.*;
import com.java_backend.learning.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @PostMapping("/{orderCode}/items")
    public Order addItem(
            @PathVariable String orderCode,
            @RequestBody OrderItem item) {

        return service.addItem(orderCode, item);
    }
}