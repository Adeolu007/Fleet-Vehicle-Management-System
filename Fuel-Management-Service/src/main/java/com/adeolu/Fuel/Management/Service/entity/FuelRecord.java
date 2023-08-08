package com.adeolu.Fuel.Management.Service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "fuel")
public class FuelRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverName;
    private String licensePlate;
    private LocalDate refuelingDate;
    private double litersFilled;
    private double costPerLiter;
    private double totalCost;
}
