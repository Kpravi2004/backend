package com.java_backend.learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    private String status;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "seat_ids")
    private String seatIds;

    @Column(name = "table_numbers")
    private String tableNumbers;

    @Column(name = "waiter_names")
    private String waiterNames;

    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillItem> items;

    // Constructors
    public Bill() {}

    public Bill(LocalDateTime dateTime, String status, Double totalAmount, String seatIds, String paymentMethod) {
        this.dateTime = dateTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.seatIds = seatIds;
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

    public String getSeatIds() { return seatIds; }
    public void setSeatIds(String seatIds) { this.seatIds = seatIds; }

    public String getTableNumbers() { return tableNumbers; }
    public void setTableNumbers(String tableNumbers) { this.tableNumbers = tableNumbers; }

    public String getWaiterNames() { return waiterNames; }
    public void setWaiterNames(String waiterNames) { this.waiterNames = waiterNames; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
}