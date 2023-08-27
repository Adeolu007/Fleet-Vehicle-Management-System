package com.adeolu.Reservation.Service.dto;

import lombok.*;

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
