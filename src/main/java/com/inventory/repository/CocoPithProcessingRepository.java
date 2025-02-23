package com.inventory.repository;

import com.inventory.model.CocoPithProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CocoPithProcessingRepository extends JpaRepository<CocoPithProcessing, Long> {
    List<CocoPithProcessing> findByProcessingTimeBetween(LocalDateTime start, LocalDateTime end);

    List<CocoPithProcessing> findTop10ByOrderByProcessingTimeDesc();
}