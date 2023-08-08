package com.adeolu.Vehicle.Management.Service.controller;

import com.adeolu.Vehicle.Management.Service.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.dto.VehicleRegistration;
import com.adeolu.Vehicle.Management.Service.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<String> registerVehicle(@RequestBody VehicleRegistration vehicleRegistration) {
        return vehicleService.registerNewVehicle(vehicleRegistration);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<RegisteredVehicleDto> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return vehicleService.findByLicensePlate(licensePlate);
    }

    //not working

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String licensePlate) {
        return vehicleService.deleteVehicle(licensePlate);
    }

    @PutMapping("/update")
    public ResponseEntity<RegisteredVehicleDto> updateVehicle(@RequestBody VehicleRegistration vehicleRegistration) {
        return vehicleService.updateVehicle(vehicleRegistration);
    }
}
