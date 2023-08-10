package com.adeolu.Maintenance.and.Repair.Service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairRecordDto {
    private String vehicle;
    private String description;
    private LocalDate repairDate;
    private BigDecimal cost;
}