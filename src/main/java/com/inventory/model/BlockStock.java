package com.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "block_stock")
@NoArgsConstructor
public class BlockStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public BlockStock(Double quantity) {
        this.quantity = quantity;
        this.updatedAt = LocalDateTime.now();
    }
}