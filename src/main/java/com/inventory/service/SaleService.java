package com.inventory.service;

import com.inventory.model.Sale;
import com.inventory.model.ProductType;
import com.inventory.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final StockService stockService;
    private final BlockProductionService blockProductionService;
    private static final double BALE_WEIGHT = 100.0; // kg per bale

    @Transactional
    public Sale createSale(Sale sale) {
        validateAndUpdateStock(sale);
        return saleRepository.save(sale);
    }

    private void validateAndUpdateStock(Sale sale) {
        switch (sale.getProductType()) {
            case FIBER_WHITE:
                double whiteFiberQuantity = sale.getQuantity() * BALE_WEIGHT;
                stockService.reduceWhiteFiberStock(whiteFiberQuantity);
                break;
            case FIBER_BROWN:
                double brownFiberQuantity = sale.getQuantity() * BALE_WEIGHT;
                stockService.reduceBrownFiberStock(brownFiberQuantity);
                break;
            case PITH_REGULAR:
                stockService.reducePithStock(sale.getQuantity());
                break;
            case PITH_LOW_EC:
                stockService.reduceLowECPithStock(sale.getQuantity());
                break;
            case BLOCK:
                stockService.reduceBlockStock(sale.getQuantity());
                break;
            default:
                throw new IllegalArgumentException("Invalid product type");
        }
    }

    public List<Sale> getRecentSales() {
        return saleRepository.findTop10ByOrderBySaleDateDesc();
    }

    public List<Sale> getSalesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return saleRepository.findBySaleDateBetweenOrderBySaleDateDesc(startOfDay, endOfDay);
    }
}