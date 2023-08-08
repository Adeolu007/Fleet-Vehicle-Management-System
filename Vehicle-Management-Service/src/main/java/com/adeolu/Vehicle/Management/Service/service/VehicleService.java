package com.adeolu.Vehicle.Management.Service.service;

import com.adeolu.Vehicle.Management.Service.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.dto.VehicleRegistration;
import org.springframework.http.ResponseEntity;

public interface VehicleService {
    ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration);
    ResponseEntity<RegisteredVehicleDto> findByLicensePlate(String licensePlate);
    ResponseEntity<String> deleteVehicle(String licensePlate);
    ResponseEntity<RegisteredVehicleDto> updateVehicle(VehicleRegistration vehicleRegistration);

}
