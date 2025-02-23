package com.inventory.service;

import com.inventory.model.WhiteFiberStock;
import com.inventory.repository.WhiteFiberStockRepository;
import com.inventory.service.impl.WhiteFiberStockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WhiteFiberStockServiceImplTest {
    @Mock
    private WhiteFiberStockRepository repository;

    @InjectMocks
    private WhiteFiberStockServiceImpl service;

    @Test
    void getCurrentStock_WhenNoStock_ReturnsZero() {
        when(repository.findTopByOrderByUpdatedAtDesc()).thenReturn(Optional.empty());
        assertEquals(0.0, service.getCurrentStock());
    }
}