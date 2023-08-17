package com.adeolu.Vehicle.Tracking.Service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "vehicle_tracked")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleLicenseNumber;
    private Double latitude;
    private Double longitude;
}
