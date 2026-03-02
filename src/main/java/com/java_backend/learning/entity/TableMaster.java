package com.java_backend.learning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "table_entity")
public class TableMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId;

    @NotNull
    @JsonProperty("table_number")
    private Integer tableNumber;

    @NotBlank
    @JsonProperty("table_name")
    private String tableName;

    @NotNull
    @JsonProperty("total_seats")
    private Integer totalSeats;

    @NotBlank
    private String status; // Active / Inactive

    @NotBlank
    @JsonProperty("floor_name")
    private String floorName;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SeatMaster> seats = new ArrayList<>();

    // Getters & Setters
    public Integer getTableId() { return tableId; }
    public void setTableId(Integer tableId) { this.tableId = tableId; }

    public Integer getTableNumber() { return tableNumber; }
    public void setTableNumber(Integer tableNumber) { this.tableNumber = tableNumber; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFloorName() { return floorName; }
    public void setFloorName(String floorName) { this.floorName = floorName; }

    public List<SeatMaster> getSeats() { return seats; }
    public void setSeats(List<SeatMaster> seats) { this.seats = seats; }
}