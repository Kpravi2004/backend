package com.java_backend.learning.service;

import com.java_backend.learning.dto.BillRequest;
import com.java_backend.learning.dto.BillResponse;
import com.java_backend.learning.dto.ItemDto;
import com.java_backend.learning.entity.Bill;
import com.java_backend.learning.entity.BillItem;
import com.java_backend.learning.entity.SeatMaster;
import com.java_backend.learning.repository.BillRepository;
import com.java_backend.learning.repository.BillItemRepository;
import com.java_backend.learning.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public BillResponse createBill(BillRequest request) {
        Bill bill = new Bill();
        bill.setDateTime(LocalDateTime.now());
        String status = request.getStatus() != null ? request.getStatus() : "pending";
        bill.setStatus(status);
        bill.setTotalAmount(request.getTotal());
        
        if (request.getSeatIds() != null && !request.getSeatIds().isEmpty()) {
            String seatIdsStr = request.getSeatIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            bill.setSeatIds(seatIdsStr);
        }
        Bill savedBill = billRepository.save(bill);

        List<BillItem> items = request.getItems().stream().map(itemDto -> {
            BillItem item = new BillItem();
            item.setItemName(itemDto.getProductName());
            item.setProductCode(itemDto.getProductCode());
            item.setPrice(itemDto.getUnitPrice());
            item.setQuantity(itemDto.getQuantity());
            item.setBill(savedBill);
            return item;
        }).collect(Collectors.toList());
        billItemRepository.saveAll(items);
        savedBill.setItems(items);

        // Update seat billing status and collect table/waiter info
        if (request.getSeatIds() != null && !request.getSeatIds().isEmpty()) {
            for (Integer seatId : request.getSeatIds()) {
                seatRepository.updateBillingStatus(seatId, true);
            }
            // Fetch seats with table and waiter to populate bill fields
            List<SeatMaster> seats = seatRepository.findSeatsWithTableAndWaiter(request.getSeatIds());
            Set<String> tableNumbers = new HashSet<>();
            Set<String> waiterNames = new HashSet<>();
            for (SeatMaster seat : seats) {
                if (seat.getTable() != null) {
                    tableNumbers.add(String.valueOf(seat.getTable().getTableNumber()));
                    if (seat.getTable().getWaiter() != null) {
                        waiterNames.add(seat.getTable().getWaiter().getWaiterName());
                    }
                }
            }
            savedBill.setTableNumbers(String.join(", ", tableNumbers));
            savedBill.setWaiterNames(String.join(", ", waiterNames));
            billRepository.save(savedBill);
        }

        return getBillResponse(savedBill);
    }

    public List<BillResponse> getAllBills() {
        return billRepository.findAll().stream()
                .map(this::getBillResponse)
                .collect(Collectors.toList());
    }

    public List<BillResponse> getBillsByStatus(String status) {
        return billRepository.findByStatus(status).stream()
                .map(this::getBillResponse)
                .collect(Collectors.toList());
    }

    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return getBillResponse(bill);
    }

    @Transactional
    public BillResponse confirmBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setStatus("completed");
        Bill savedBill = billRepository.save(bill);

        if (savedBill.getSeatIds() != null && !savedBill.getSeatIds().isEmpty()) {
            List<Integer> seatIdList = Arrays.stream(savedBill.getSeatIds().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            for (Integer seatId : seatIdList) {
                seatRepository.updateSeatStatusAndBilling(seatId, "Free", false);
            }
        }

        return getBillResponse(savedBill);
    }

    private BillResponse getBillResponse(Bill bill) {
        List<ItemDto> itemDtos = bill.getItems() != null ?
            bill.getItems().stream().map(item -> {
                ItemDto dto = new ItemDto();
                dto.setProductCode(item.getProductCode());
                dto.setProductName(item.getItemName());
                dto.setQuantity(item.getQuantity());
                dto.setUnitPrice(item.getPrice());
                dto.setSubtotal(item.getQuantity() * item.getPrice());
                return dto;
            }).collect(Collectors.toList()) : Collections.emptyList();

        return new BillResponse(
            bill.getId(),
            bill.getDateTime(),
            bill.getStatus(),
            bill.getTotalAmount(),
            itemDtos,
            bill.getTableNumbers(),
            bill.getWaiterNames()
        );
    }
}