package com.java_backend.learning.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "seat_entity")
public class SeatMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    @JsonProperty("seat_no")
    private Integer seatNo;

    private String status; // Free / Occupied / Reserved

    @JsonProperty("color_code")
    private String colorCode;

    @JsonProperty("billing_status") // will appear in JSON
    private Boolean billingStatus = false; // default false

    @ManyToOne
    @JoinColumn(name = "table_id")
    @JsonBackReference
    private TableMaster table;

    // Getters & Setters
    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }

    public Integer getSeatNo() { return seatNo; }
    public void setSeatNo(Integer seatNo) { this.seatNo = seatNo; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;

        // Auto Color Mapping
        if ("Occupied".equalsIgnoreCase(status)) {
            this.colorCode = "Green";
        } else if ("Reserved".equalsIgnoreCase(status)) {
            this.colorCode = "Orange";
        } else {
            this.colorCode = "White";
        }
    }

    public String getColorCode() { return colorCode; }
    public void setColorCode(String colorCode) { this.colorCode = colorCode; }

    public Boolean getBillingStatus() { return billingStatus; }
    public void setBillingStatus(Boolean billingStatus) { this.billingStatus = billingStatus; }

    public TableMaster getTable() { return table; }
    public void setTable(TableMaster table) { this.table = table; }
}