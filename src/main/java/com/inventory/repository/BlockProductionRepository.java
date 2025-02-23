package com.inventory.repository;

import com.inventory.model.BlockProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlockProductionRepository extends JpaRepository<BlockProduction, Long> {
    List<BlockProduction> findByProductionTimeBetween(LocalDateTime start, LocalDateTime end);

    List<BlockProduction> findTop10ByOrderByProductionTimeDesc();
}