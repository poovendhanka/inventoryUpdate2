package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "coco_pith_processing")
@NoArgsConstructor
public class CocoPithProcessing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Input quantity is required")
    @Min(value = 1, message = "Input quantity must be at least 1")
    @Column(name = "input_quantity")
    private Double inputQuantity;

    @Column(name = "output_quantity")
    private Double outputQuantity;

    @NotNull(message = "Processing time is required")
    @Column(name = "processing_time")
    private LocalDateTime processingTime;

    @Column(name = "system_time")
    private LocalDateTime systemTime;

    @Column(name = "supervisor_name")
    private String supervisorName;

    @PrePersist
    public void prePersist() {
        this.systemTime = LocalDateTime.now();
        if (this.processingTime == null) {
            this.processingTime = LocalDateTime.now();
        }
    }
}