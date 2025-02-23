package com.inventory.service;

import com.inventory.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final PithStockService pithStockService;
    private final WhiteFiberStockService whiteFiberStockService;
    private final BrownFiberStockService brownFiberStockService;
    private final LowECPithStockService lowECPithStockService;
    private final BlockStockService blockStockService;

    public Double getCurrentPithStock() {
        return pithStockService.getCurrentStock();
    }

    public Double getCurrentWhiteFiberStock() {
        return whiteFiberStockService.getCurrentStock();
    }

    public Double getCurrentBrownFiberStock() {
        return brownFiberStockService.getCurrentStock();
    }

    public Double getCurrentLowECPithStock() {
        return lowECPithStockService.getCurrentStock();
    }

    public Double getCurrentBlockStock() {
        return blockStockService.getCurrentStock();
    }

    @Transactional
    public void reducePithStock(Double quantity) {
        Double currentStock = getCurrentPithStock();
        if (currentStock < quantity) {
            throw new RuntimeException("Insufficient pith stock");
        }
        pithStockService.addStock(-quantity);
    }

    @Transactional
    public void reduceWhiteFiberStock(Double quantity) {
        Double currentStock = getCurrentWhiteFiberStock();
        if (currentStock < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient white fiber stock. Required: %.2f, Available: %.2f",
                            quantity, currentStock));
        }
        whiteFiberStockService.addStock(-quantity);
    }

    @Transactional
    public void reduceBrownFiberStock(Double quantity) {
        Double currentStock = getCurrentBrownFiberStock();
        if (currentStock < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient brown fiber stock. Required: %.2f, Available: %.2f",
                            quantity, currentStock));
        }
        brownFiberStockService.addStock(-quantity);
    }

    @Transactional
    public void reduceLowECPithStock(Double quantity) {
        Double currentStock = getCurrentLowECPithStock();
        if (currentStock < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient low EC pith stock. Required: %.2f, Available: %.2f",
                            quantity, currentStock));
        }
        lowECPithStockService.addStock(-quantity);
    }

    @Transactional
    public void reduceBlockStock(Double quantity) {
        Double currentStock = getCurrentBlockStock();
        if (currentStock < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient block stock. Required: %.2f, Available: %.2f",
                            quantity, currentStock));
        }
        blockStockService.addStock(-quantity);
    }
}