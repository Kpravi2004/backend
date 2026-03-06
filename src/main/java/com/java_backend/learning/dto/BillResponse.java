package com.java_backend.learning.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BillResponse {
    private Long id;
    private LocalDateTime dateTime;
    private String status;
    private Double totalAmount;
    private List<ItemDto> items;

    public BillResponse() {}

    public BillResponse(Long id, LocalDateTime dateTime, String status, Double totalAmount, List<ItemDto> items) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }
}