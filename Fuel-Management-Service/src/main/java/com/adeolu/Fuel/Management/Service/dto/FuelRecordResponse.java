package com.adeolu.Fuel.Management.Service.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelRecordResponse {
    private String licensePlate;
    private String driverName;
    private LocalDate refuelingDate;
    private double litersFilled;
    private double costPerLiter;
    private double totalCost;
}
