package com.adeolu.Vehicle.Management.Service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
//    @ManyToOne
//    @JoinColumn(name="employeeid", insertable=false, updatable=false)
//    private Employee inCharge;
//    private Integer employeeid;
//    @ManyToOne
//    @JoinColumn(name="locationid", insertable=false, updatable=false)
//    private Location currentLocation;
//    private Integer locationid;
}
