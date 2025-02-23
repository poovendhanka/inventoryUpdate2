package com.inventory.service.impl;

import com.inventory.model.LowECPithStock;
import com.inventory.repository.LowECPithStockRepository;
import com.inventory.service.LowECPithStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LowECPithStockServiceImpl implements LowECPithStockService {

    private final LowECPithStockRepository lowECPithStockRepository;

    @Override
    @Transactional
    public void addStock(Double quantity) {
        LowECPithStock currentStock = lowECPithStockRepository.findTopByOrderByUpdatedAtDesc()
                .orElse(new LowECPithStock(0.0));

        LowECPithStock newStock = new LowECPithStock();
        newStock.setQuantity(currentStock.getQuantity() + quantity);
        newStock.setUpdatedAt(LocalDateTime.now());
        lowECPithStockRepository.save(newStock);
    }

    @Override
    public Double getCurrentStock() {
        return lowECPithStockRepository.findTopByOrderByUpdatedAtDesc()
                .map(LowECPithStock::getQuantity)
                .orElse(0.0);
    }
}