package com.java_backend.learning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bill_item")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "product_code")
    private String productCode;

    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    // Constructors
    public BillItem() {}

    public BillItem(String itemName, String productCode, Double price, Integer quantity, Bill bill) {
        this.itemName = itemName;
        this.productCode = productCode;
        this.price = price;
        this.quantity = quantity;
        this.bill = bill;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }
}