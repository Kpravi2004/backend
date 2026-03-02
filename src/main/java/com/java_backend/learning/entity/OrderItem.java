package com.java_backend.learning.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productCode;
    private String productName;

    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        this.subtotal = quantity * unitPrice;
    }

    // Getters & Setters

    public Integer getId() { return id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public Double getSubtotal() { return subtotal; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}