package com.inventory.service.impl;

import com.inventory.model.BrownFiberStock;
import com.inventory.repository.BrownFiberStockRepository;
import com.inventory.service.BrownFiberStockService;
import com.inventory.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BrownFiberStockServiceImpl implements BrownFiberStockService {

    private final BrownFiberStockRepository brownFiberStockRepository;

    @Override
    @Transactional
    public void addStock(Double quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        BrownFiberStock currentStock = brownFiberStockRepository.findTopByOrderByUpdatedAtDesc()
                .orElse(new BrownFiberStock(0.0));

        Double newQuantity = currentStock.getQuantity() + quantity;
        if (newQuantity < 0) {
            throw new InsufficientStockException(
                    String.format("Cannot reduce stock below 0. Current: %.2f, Requested: %.2f",
                            currentStock.getQuantity(), Math.abs(quantity)));
        }

        BrownFiberStock newStock = new BrownFiberStock();
        newStock.setQuantity(newQuantity);
        newStock.setUpdatedAt(LocalDateTime.now());
        brownFiberStockRepository.save(newStock);
    }

    @Override
    public Double getCurrentStock() {
        return brownFiberStockRepository.findTopByOrderByUpdatedAtDesc()
                .map(BrownFiberStock::getQuantity)
                .orElse(0.0);
    }
}