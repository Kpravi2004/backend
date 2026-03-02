package com.java_backend.learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String orderCode;

    private Integer tableId;
    private Integer waiterId;

    private Double total;

    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();

   @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
   private List<OrderItem> items;

    public List<OrderItem> getItems() {
         return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }    // Getters & Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }

    public Integer getTableId() { return tableId; }
    public void setTableId(Integer tableId) { this.tableId = tableId; }

    public Integer getWaiterId() { return waiterId; }
    public void setWaiterId(Integer waiterId) { this.waiterId = waiterId; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}