package com.java_backend.learning.service;

import com.java_backend.learning.dto.BillRequest;
import com.java_backend.learning.dto.BillResponse;
import com.java_backend.learning.dto.ItemDto;
import com.java_backend.learning.entity.Bill;
import com.java_backend.learning.entity.BillItem;
import com.java_backend.learning.repository.BillRepository;
import com.java_backend.learning.repository.BillItemRepository;
import com.java_backend.learning.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    // 1. Create bill header
    Bill bill = new Bill();
    bill.setDateTime(LocalDateTime.now());
    // Use status from request if provided, otherwise "pending"
    String status = request.getStatus() != null ? request.getStatus() : "pending";
    bill.setStatus(status);
    bill.setTotalAmount(request.getTotal());
    Bill savedBill = billRepository.save(bill);

    // 2. Create bill items (including productCode)
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

    // 3. Update seat billing status
    if (request.getSeatIds() != null) {
        for (Integer seatId : request.getSeatIds()) {
            seatRepository.updateBillingStatus(seatId, true);
        }
    }

    // 4. Prepare response (include productCode)
    List<ItemDto> itemDtos = items.stream().map(item -> {
        ItemDto dto = new ItemDto();
        dto.setProductCode(item.getProductCode());
        dto.setProductName(item.getItemName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getPrice());
        dto.setSubtotal(item.getQuantity() * item.getPrice());
        return dto;
    }).collect(Collectors.toList());

    return new BillResponse(
        savedBill.getId(),
        savedBill.getDateTime(),
        savedBill.getStatus(),
        savedBill.getTotalAmount(),
        itemDtos
    );
}

    public List<BillResponse> getAllBills() {
        return billRepository.findAll().stream().map(bill -> {
            List<ItemDto> itemDtos = bill.getItems().stream().map(item -> {
                ItemDto dto = new ItemDto();
                dto.setProductCode(item.getProductCode());   // <-- new
                dto.setProductName(item.getItemName());
                dto.setQuantity(item.getQuantity());
                dto.setUnitPrice(item.getPrice());
                dto.setSubtotal(item.getQuantity() * item.getPrice());
                return dto;
            }).collect(Collectors.toList());

            return new BillResponse(
                bill.getId(),
                bill.getDateTime(),
                bill.getStatus(),
                bill.getTotalAmount(),
                itemDtos
            );
        }).collect(Collectors.toList());
    }

    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
        List<ItemDto> itemDtos = bill.getItems().stream().map(item -> {
            ItemDto dto = new ItemDto();
            dto.setProductCode(item.getProductCode());       // <-- new
            dto.setProductName(item.getItemName());
            dto.setQuantity(item.getQuantity());
            dto.setUnitPrice(item.getPrice());
            dto.setSubtotal(item.getQuantity() * item.getPrice());
            return dto;
        }).collect(Collectors.toList());

        return new BillResponse(
            bill.getId(),
            bill.getDateTime(),
            bill.getStatus(),
            bill.getTotalAmount(),
            itemDtos
        );
    }
}