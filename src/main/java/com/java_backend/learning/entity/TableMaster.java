package com.java_backend.learning.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "table_entity")
public class TableMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId;

    @JsonProperty("table_number")
    private Integer tableNumber;

    @JsonProperty("table_name")
    private String tableName;

    @JsonProperty("total_seats")
    private Integer totalSeats;

    private String status; // Active / Inactive

    @JsonProperty("floor_name")
    private String floorName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waiter_id")
    private Waiter waiter;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<SeatMaster> seats = new ArrayList<>();

    @Transient
    private Integer waiterId;

    @Transient
    private String waiterName;

    // Constructors
    public TableMaster() {}

    // Getters and setters
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

    public Waiter getWaiter() { return waiter; }
    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
        // Update transient fields
        if (waiter != null) {
            this.waiterId = waiter.getWaiterId();
            this.waiterName = waiter.getWaiterName();
        } else {
            this.waiterId = null;
            this.waiterName = null;
        }
    }

    @JsonProperty("waiterId")
    public Integer getWaiterId() { return waiterId; }

    @JsonProperty("waiterName")
    public String getWaiterName() { return waiterName; }

    public List<SeatMaster> getSeats() { return seats; }
    public void setSeats(List<SeatMaster> seats) { this.seats = seats; }

    // Lifecycle callback to populate transient fields after load
    @PostLoad
    private void postLoad() {
        if (waiter != null) {
            this.waiterId = waiter.getWaiterId();
            this.waiterName = waiter.getWaiterName();
        }
    }
}