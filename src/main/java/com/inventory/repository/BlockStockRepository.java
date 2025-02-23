package com.inventory.repository;

import com.inventory.model.BlockStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlockStockRepository extends JpaRepository<BlockStock, Long> {
    Optional<BlockStock> findTopByOrderByUpdatedAtDesc();
}