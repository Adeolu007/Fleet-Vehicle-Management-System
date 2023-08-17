package com.adeolu.Vehicle.Tracking.Service.controller;


import com.adeolu.Vehicle.Tracking.Service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/{vehicleId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable String vehicleId,
                                            @RequestParam double latitude,
                                            @RequestParam double longitude) {
        vehicleService.updateVehicleLocation(vehicleId, latitude, longitude);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
