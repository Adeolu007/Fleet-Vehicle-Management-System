package com.adeolu.Driver.Management.Service.Gradle.service;

import com.adeolu.Driver.Management.Service.Gradle.dto.DriverInfo;
import com.adeolu.Driver.Management.Service.Gradle.dto.DriverRegistration;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {

    ResponseEntity<String> createDriver(DriverRegistration driverRegistration);
    ResponseEntity<DriverInfo> getDriverByLicenseNumber(String licenseNumber);
    ResponseEntity<List<DriverInfo>> getAllDrivers();
    ResponseEntity<DriverInfo> updateDriver(DriverRegistration driverRegistration);
    ResponseEntity<String> deleteDriver(String licenseNumber);
    ResponseEntity<Boolean> doesDriverExist (String licenseNumber);

}
