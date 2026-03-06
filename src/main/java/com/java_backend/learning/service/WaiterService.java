package com.java_backend.learning.service;

import com.java_backend.learning.entity.Waiter;
import com.java_backend.learning.repository.WaiterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaiterService {

    private final WaiterRepository repository;

    public WaiterService(WaiterRepository repository) {
        this.repository = repository;
    }

    public Waiter create(Waiter waiter) {
        return repository.save(waiter);
    }

    public List<Waiter> getAll() {
        return repository.findAll();
    }

    public Waiter getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Waiter not found"));
    }

    public Waiter update(Integer id, Waiter updated) {
        Waiter waiter = getById(id);
        waiter.setWaiterName(updated.getWaiterName());
        return repository.save(waiter);
    }

    public Waiter patch(Integer id, Waiter updated) {
        Waiter waiter = getById(id);
        if (updated.getWaiterName() != null)
            waiter.setWaiterName(updated.getWaiterName());
        return repository.save(waiter);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}