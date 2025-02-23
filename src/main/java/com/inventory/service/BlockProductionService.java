package com.inventory.service;

import com.inventory.exception.InsufficientStockException;
import com.inventory.model.BlockProduction;
import com.inventory.model.PithType;
import com.inventory.repository.BlockProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockProductionService {

    private final BlockProductionRepository blockProductionRepository;
    private final PithStockService pithStockService;
    private final LowECPithStockService lowECPithStockService;

    private static final double PITH_PER_BLOCK = 5.0; // 5kg per block

    @Transactional
    public BlockProduction createBlocks(BlockProduction production) {
        double requiredPith = production.getNumberOfBlocks() * PITH_PER_BLOCK;

        // Check stock based on pith type
        if (production.getPithType() == PithType.REGULAR) {
            if (pithStockService.getCurrentStock() < requiredPith) {
                throw new InsufficientStockException(
                        String.format("Insufficient regular pith stock. Required: %.2f, Available: %.2f",
                                requiredPith, pithStockService.getCurrentStock()));
            }
            pithStockService.addStock(-requiredPith);
        } else {
            if (lowECPithStockService.getCurrentStock() < requiredPith) {
                throw new InsufficientStockException(
                        String.format("Insufficient low EC pith stock. Required: %.2f, Available: %.2f",
                                requiredPith, lowECPithStockService.getCurrentStock()));
            }
            lowECPithStockService.addStock(-requiredPith);
        }

        return blockProductionRepository.save(production);
    }

    public List<BlockProduction> getRecentProductions() {
        return blockProductionRepository.findTop10ByOrderByProductionTimeDesc();
    }
}