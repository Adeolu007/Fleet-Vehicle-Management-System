package com.adeolu.Fuel.Management.Service.controller;

import com.adeolu.Fuel.Management.Service.dto.FuelRecordDto;
import com.adeolu.Fuel.Management.Service.dto.FuelRecordResponse;
import com.adeolu.Fuel.Management.Service.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    @Autowired
    private FuelService fuelService;

    @PostMapping("/add")
    public ResponseEntity<FuelRecordResponse> addFuelRecord(@RequestBody FuelRecordDto fuelRecordDto) {
        return fuelService.addFuelRecord(fuelRecordDto);
    }

    @GetMapping("/records/{licensePlate}")
    public ResponseEntity<List<FuelRecordResponse>> getFuelRecordsByLicensePlate(@PathVariable String licensePlate) {
        return fuelService.getFuelRecordsByLicensePlate(licensePlate);
    }

    @GetMapping("/efficiency/{licensePlate}")
    public ResponseEntity<Double> calculateFuelEfficiency(@PathVariable String licensePlate) {
        double fuelEfficiency = fuelService.calculateFuelEfficiency(licensePlate);
        return ResponseEntity.ok(fuelEfficiency);
    }
}
