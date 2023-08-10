package com.adeolu.Maintenance.and.Repair.Service.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceTaskDto {
    private String vehicle;
    private String description;
    private LocalDate scheduledDate;
}