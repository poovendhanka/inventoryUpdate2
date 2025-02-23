package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "block_production")
@NoArgsConstructor
public class BlockProduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Pith type must be selected")
    @Enumerated(EnumType.STRING)
    @Column(name = "pith_type")
    private PithType pithType;

    @NotNull(message = "Number of blocks is required")
    @Min(value = 1, message = "Must produce at least 1 block")
    @Column(name = "number_of_blocks")
    private Integer numberOfBlocks;

    @NotNull(message = "Production time is required")
    @Column(name = "production_time")
    private LocalDateTime productionTime;

    @Column(name = "system_time")
    private LocalDateTime systemTime;

    @Column(name = "supervisor_name")
    private String supervisorName;

    @PrePersist
    public void prePersist() {
        this.systemTime = LocalDateTime.now();
        if (this.productionTime == null) {
            this.productionTime = LocalDateTime.now();
        }
    }
}