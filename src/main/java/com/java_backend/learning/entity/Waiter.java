package com.java_backend.learning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "waiter_entity")
public class Waiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer waiterId;

    private String waiterName;

    // Getters & Setters
    public Integer getWaiterId() { return waiterId; }
    public void setWaiterId(Integer waiterId) { this.waiterId = waiterId; }

    public String getWaiterName() { return waiterName; }
    public void setWaiterName(String waiterName) { this.waiterName = waiterName; }
}