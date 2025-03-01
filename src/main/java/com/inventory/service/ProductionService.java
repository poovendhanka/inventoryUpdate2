package com.inventory.service;

import com.inventory.model.Production;
import com.inventory.model.ShiftType;
import com.inventory.model.PithStock;
import com.inventory.model.FiberStock;
import com.inventory.repository.ProductionRepository;
import com.inventory.repository.PithStockRepository;
import com.inventory.repository.FiberStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.inventory.model.HuskType;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final PithStockService pithStockService;
    private final WhiteFiberStockService whiteFiberStockService;
    private final BrownFiberStockService brownFiberStockService;

    @Transactional
    public Production createProduction(Production production) {
        try {
            production.prePersist(); // This will set the fiber bales based on husk type
            Production savedProduction = productionRepository.save(production);

            // Update pith stock
            pithStockService.addStock(production.getPithQuantity());

            // Update fiber stock based on husk type
            if (production.getHuskType() == HuskType.GREEN_HUSK) {
                whiteFiberStockService.addStock(Double.valueOf(production.getWhiteFiberBales()));
            } else {
                brownFiberStockService.addStock(Double.valueOf(production.getBrownFiberBales()));
            }

            return savedProduction;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create production: " + e.getMessage(), e);
        }
    }

    private LocalDate getAdjustedDate(LocalDateTime dateTime) {
        if (dateTime.getHour() >= 0 && dateTime.getHour() < 8) {
            return dateTime.toLocalDate().minusDays(1);
        }
        return dateTime.toLocalDate();
    }

    public List<Production> getProductionsByDateAndShift(LocalDate date, ShiftType shift) {
        List<Production> productions = productionRepository
                .findByProductionDateAndShiftOrderByBatchCompletionTimeAsc(date, shift);

        LocalDateTime previousBatchTime = null;
        for (Production prod : productions) {
            prod.calculateTimeTaken(previousBatchTime);
            previousBatchTime = prod.getBatchCompletionTime();
        }

        return productions;
    }

    public List<Production> getRecentProductions(LocalDate date) {
        return productionRepository.findByProductionDateOrderByBatchCompletionTimeDesc(date);
    }
}