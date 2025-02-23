package com.inventory.service;

import com.inventory.exception.InsufficientStockException;
import com.inventory.model.CocoPithProcessing;
import com.inventory.model.PithType;
import com.inventory.repository.CocoPithProcessingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CocoPithProcessingService {

    private final CocoPithProcessingRepository cocoPithProcessingRepository;
    private final PithStockService pithStockService;
    private final LowECPithStockService lowECPithStockService;

    private static final double CONVERSION_RATE = 0.9; // 90% yield

    @Transactional
    public CocoPithProcessing processPith(CocoPithProcessing processing) {
        validateProcessing(processing);

        double outputQuantity = calculateOutputQuantity(processing.getInputQuantity());
        processing.setOutputQuantity(outputQuantity);

        // Update stocks
        pithStockService.addStock(-processing.getInputQuantity());
        lowECPithStockService.addStock(outputQuantity);

        return cocoPithProcessingRepository.save(processing);
    }

    private void validateProcessing(CocoPithProcessing processing) {
        if (processing.getInputQuantity() == null || processing.getInputQuantity() <= 0) {
            throw new IllegalArgumentException("Input quantity must be greater than 0");
        }

        Double currentStock = pithStockService.getCurrentStock();
        if (currentStock < processing.getInputQuantity()) {
            throw new InsufficientStockException(
                    String.format("Insufficient regular pith stock. Required: %.2f kg, Available: %.2f kg",
                            processing.getInputQuantity(), currentStock));
        }
    }

    private Double calculateOutputQuantity(Double inputQuantity) {
        return inputQuantity * CONVERSION_RATE;
    }

    public List<CocoPithProcessing> getRecentProcessings() {
        return cocoPithProcessingRepository.findTop10ByOrderByProcessingTimeDesc();
    }
}