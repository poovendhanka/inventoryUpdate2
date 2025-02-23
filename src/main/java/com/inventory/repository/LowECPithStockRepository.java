package com.inventory.repository;

import com.inventory.model.LowECPithStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LowECPithStockRepository extends JpaRepository<LowECPithStock, Long> {
    Optional<LowECPithStock> findTopByOrderByUpdatedAtDesc();
}