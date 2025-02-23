package com.inventory.service.impl;

import com.inventory.model.BlockStock;
import com.inventory.repository.BlockStockRepository;
import com.inventory.service.BlockStockService;
import com.inventory.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlockStockServiceImpl implements BlockStockService {

    private final BlockStockRepository blockStockRepository;

    @Override
    @Transactional
    public void addStock(Double quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        BlockStock currentStock = blockStockRepository.findTopByOrderByUpdatedAtDesc()
                .orElse(new BlockStock(0.0));

        Double newQuantity = currentStock.getQuantity() + quantity;
        if (newQuantity < 0) {
            throw new InsufficientStockException(
                    String.format("Cannot reduce block stock below 0. Current: %.2f, Requested: %.2f",
                            currentStock.getQuantity(), Math.abs(quantity)));
        }

        BlockStock newStock = new BlockStock();
        newStock.setQuantity(newQuantity);
        newStock.setUpdatedAt(LocalDateTime.now());
        blockStockRepository.save(newStock);
    }

    @Override
    public Double getCurrentStock() {
        return blockStockRepository.findTopByOrderByUpdatedAtDesc()
                .map(BlockStock::getQuantity)
                .orElse(0.0);
    }
}