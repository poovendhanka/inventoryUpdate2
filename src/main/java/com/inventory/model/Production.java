package com.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import java.time.Duration;

@Data
@Entity
@Table(name = "production")
@NoArgsConstructor
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_number")
    private Integer batchNumber;

    @NotNull(message = "Number of bales is required")
    @Min(value = 1, message = "Must have at least 1 bale")
    @Column(name = "num_bales")
    private Integer numBales = 18;

    @NotNull(message = "Number of boxes is required")
    @Min(value = 1, message = "Must have at least 1 box")
    @Column(name = "num_boxes")
    private Integer numBoxes = 1;

    @NotNull(message = "Batch completion time is required")
    @Column(name = "batch_completion_time")
    private LocalDateTime batchCompletionTime;

    @Column(name = "system_time")
    private LocalDateTime systemTime;

    @NotBlank(message = "Supervisor name is required")
    @Column(name = "supervisor_name")
    private String supervisorName;

    @NotNull(message = "Shift is required")
    @Enumerated(EnumType.STRING)
    private ShiftType shift;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @NotNull(message = "Pith quantity is required")
    @Min(value = 1, message = "Pith quantity must be greater than 0")
    private Double pithQuantity = 750.0;

    @NotNull(message = "Husk type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "husk_type")
    private HuskType huskType;

    @Column(name = "white_fiber_bales")
    private Integer whiteFiberBales = 0;

    @Column(name = "brown_fiber_bales")
    private Integer brownFiberBales = 0;

    @Transient
    private Duration timeTaken;

    @PrePersist
    public void prePersist() {
        this.systemTime = LocalDateTime.now();
        if (this.productionDate == null) {
            this.productionDate = this.batchCompletionTime.toLocalDate();
        }

        // Set fiber bales based on husk type
        if (this.huskType == HuskType.GREEN_HUSK) {
            this.whiteFiberBales = this.numBales;
            this.brownFiberBales = 0;
        } else {
            this.brownFiberBales = this.numBales;
            this.whiteFiberBales = 0;
        }
    }

    public void calculateTimeTaken(LocalDateTime previousBatchTime) {
        if (previousBatchTime != null) {
            this.timeTaken = Duration.between(previousBatchTime, this.batchCompletionTime);
        }
    }
}