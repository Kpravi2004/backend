package com.java_backend.learning.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BillResponse {
    private Long id;
    private LocalDateTime dateTime;
    private String status;
    private Double totalAmount;
    private List<ItemDto> items;
    private String tableNumbers;
    private String waiterNames;
    private String paymentMethod;

    public BillResponse() {}

    public BillResponse(Long id, LocalDateTime dateTime, String status, Double totalAmount, 
                        List<ItemDto> items, String tableNumbers, String waiterNames, String paymentMethod) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
        this.tableNumbers = tableNumbers;
        this.waiterNames = waiterNames;
        this.paymentMethod = paymentMethod;
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

    public String getTableNumbers() { return tableNumbers; }
    public void setTableNumbers(String tableNumbers) { this.tableNumbers = tableNumbers; }

    public String getWaiterNames() { return waiterNames; }
    public void setWaiterNames(String waiterNames) { this.waiterNames = waiterNames; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}