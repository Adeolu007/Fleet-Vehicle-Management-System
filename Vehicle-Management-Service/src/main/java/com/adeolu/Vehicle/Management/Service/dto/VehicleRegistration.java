package com.adeolu.Vehicle.Management.Service.dto;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRegistration {
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquisitionDate;
    private String description;
    private String fuelCapacity;
}
