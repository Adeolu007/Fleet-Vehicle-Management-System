package com.adeolu.Driver.Management.Service.Gradle.controller;


import com.adeolu.Driver.Management.Service.Gradle.dto.DriverInfo;
import com.adeolu.Driver.Management.Service.Gradle.dto.DriverRegistration;
import com.adeolu.Driver.Management.Service.Gradle.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<String> createDriver(@RequestBody DriverRegistration driverRegistration) {
        return driverService.createDriver(driverRegistration);
    }

    @GetMapping("/{licenseNumber}")
    public ResponseEntity<DriverInfo> getDriverByLicenseNumber(@PathVariable String licenseNumber) {
        return driverService.getDriverByLicenseNumber(licenseNumber);
    }

    @GetMapping
    public ResponseEntity<List<DriverInfo>> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PutMapping
    public ResponseEntity<DriverInfo> updateDriver(@RequestBody DriverRegistration driverRegistration) {
        return driverService.updateDriver(driverRegistration);
    }

    @DeleteMapping("/{licenseNumber}")
    public ResponseEntity<String> deleteDriver(@PathVariable String licenseNumber) {
        return driverService.deleteDriver(licenseNumber);
    }

    @GetMapping("/exist/{licenseNumber}")
    public ResponseEntity<Boolean> doesDriverExist(@PathVariable String licenseNumber) {
        return driverService.doesDriverExist(licenseNumber);
    }
}
