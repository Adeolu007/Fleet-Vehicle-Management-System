package com.adeolu.Vehicle.Management.Service.Gradle.service;

import com.adeolu.Vehicle.Management.Service.Gradle.dto.DriverInfo;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.VehicleRegistration;
import org.springframework.http.ResponseEntity;

public interface VehicleService {
    ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration);
    ResponseEntity<RegisteredVehicleDto> findByLicensePlate(String licensePlate);
    ResponseEntity<String> deleteVehicle(String licensePlate);
    ResponseEntity<RegisteredVehicleDto> updateVehicle(VehicleRegistration vehicleRegistration);
    ResponseEntity<Boolean> doesVehicleExist (String licensePlate);

}
