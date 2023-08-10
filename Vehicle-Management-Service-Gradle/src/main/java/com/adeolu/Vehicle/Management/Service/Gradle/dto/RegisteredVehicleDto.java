package com.adeolu.Vehicle.Management.Service.Gradle.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredVehicleDto {
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private Date registrationDate;
    private Date acquisitionDate;
    private String description;
    private String fuelCapacity;
    private String firstName;
    private String driverLicenseNumber;
}
