package com.adeolu.Vehicle.Tracking.Service.repository;

import com.adeolu.Vehicle.Tracking.Service.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("select v from Vehicle v where v.vehicleLicenseNumber = ?1")
    Vehicle findByVehicleLicenseNumber(String vehicleLicenseNumber);

}
