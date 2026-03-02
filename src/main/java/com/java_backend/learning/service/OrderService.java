package com.java_backend.learning.service;

import com.java_backend.learning.entity.*;
import com.java_backend.learning.repository.*;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;

    public OrderService(OrderRepository orderRepo, OrderItemRepository itemRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
    }

    public Order create(Order order) {
        order.setTotal(0.0);
        return orderRepo.save(order);
    }

    public Order addItem(String orderCode, OrderItem item) {
        Order order = orderRepo.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        item.setOrder(order);
        OrderItem saved = itemRepo.save(item);

        recalcTotal(order);

        return orderRepo.save(order);
    }

    public void recalcTotal(Order order) {
        double total = order.getItems()
                .stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();

        order.setTotal(total);
    }
}