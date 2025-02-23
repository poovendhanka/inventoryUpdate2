package com.inventory.service.impl;

import com.inventory.model.WhiteFiberStock;
import com.inventory.repository.WhiteFiberStockRepository;
import com.inventory.service.WhiteFiberStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.inventory.exception.InsufficientStockException;

@Service
@RequiredArgsConstructor
public class WhiteFiberStockServiceImpl implements WhiteFiberStockService {

    private final WhiteFiberStockRepository whiteFiberStockRepository;

    @Override
    @Transactional
    public void addStock(Double quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        WhiteFiberStock currentStock = whiteFiberStockRepository.findTopByOrderByUpdatedAtDesc()
                .orElse(new WhiteFiberStock(0.0));

        Double newQuantity = currentStock.getQuantity() + quantity;
        if (newQuantity < 0) {
            throw new InsufficientStockException(
                    String.format("Cannot reduce stock below 0. Current: %.2f, Requested: %.2f",
                            currentStock.getQuantity(), Math.abs(quantity)));
        }

        WhiteFiberStock newStock = new WhiteFiberStock();
        newStock.setQuantity(newQuantity);
        newStock.setUpdatedAt(LocalDateTime.now());
        whiteFiberStockRepository.save(newStock);
    }

    @Override
    public Double getCurrentStock() {
        return whiteFiberStockRepository.findTopByOrderByUpdatedAtDesc()
                .map(WhiteFiberStock::getQuantity)
                .orElse(0.0);
    }
}