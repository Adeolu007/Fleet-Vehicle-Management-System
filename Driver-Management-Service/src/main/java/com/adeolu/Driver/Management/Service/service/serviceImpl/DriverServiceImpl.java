package com.adeolu.Driver.Management.Service.service.serviceImpl;

import com.adeolu.Driver.Management.Service.dto.DriverInfo;
import com.adeolu.Driver.Management.Service.dto.DriverRegistration;
import com.adeolu.Driver.Management.Service.exceptions.DriverAlreadyExistsException;
import com.adeolu.Driver.Management.Service.exceptions.DriverNotFoundException;
import com.adeolu.Driver.Management.Service.exceptions.DriversNotFoundException;
import com.adeolu.Driver.Management.Service.modal.Driver;
import com.adeolu.Driver.Management.Service.repository.DriverRepository;
import com.adeolu.Driver.Management.Service.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Override
    public ResponseEntity<String> createDriver(DriverRegistration driverRegistration) {
        // Check if driver with the same license number already exists
        if (driverRepository.existsByLicenseNumber(driverRegistration.getLicenseNumber())) {
            throw new DriverAlreadyExistsException(driverRegistration.getFirstName() + " already exists");}
        // Create a new driver profile
        Driver driver = new Driver();
        driver.setFirstName(driverRegistration.getFirstName());
        driver.setLastName(driverRegistration.getLastName());
        driver.setLicenseNumber(driverRegistration.getLicenseNumber());
        driverRepository.save(driver);
        return new ResponseEntity<>(driverRegistration.getFirstName() + "'s profile has been created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DriverInfo> getDriverByLicenseNumber(String licenseNumber) {
        if (!driverRepository.existsByLicenseNumber(licenseNumber)) {
            throw new DriverNotFoundException("Driver with license number " + licenseNumber + " not found");
        }

        Driver driverProfile = driverRepository.findByLicenseNumber(licenseNumber);
        DriverInfo driver = DriverInfo.builder()
                .firstName(driverProfile.getFirstName())
                .lastName(driverProfile.getLastName())
                .licenseNumber(driverProfile.getLicenseNumber())
                .build();

        return new ResponseEntity<>(driver, HttpStatus.FOUND);
    }

@Override
public ResponseEntity<List<DriverInfo>> getAllDrivers() {
    List<DriverInfo> driverInfos = driverRepository.findAll().stream()
            .map(driver -> DriverInfo.builder()
                    .firstName(driver.getFirstName())
                    .lastName(driver.getLastName())
                    .licenseNumber(driver.getLicenseNumber())
                    .build())
            .collect(Collectors.toList());

    if (driverInfos.isEmpty()) {
        throw new DriversNotFoundException("No drivers found");
    }

    return new ResponseEntity<>(driverInfos, HttpStatus.OK);
}

    @Override
    public ResponseEntity<DriverInfo> updateDriver(DriverRegistration driverRegistration) {
        Driver driverProfile = driverRepository.findByLicenseNumber(driverRegistration.getLicenseNumber());

        if (driverProfile == null) {
            throw new DriverNotFoundException("Driver with license number " + driverRegistration.getLicenseNumber() + " not found");}

        driverProfile.setFirstName(driverRegistration.getFirstName());
        driverProfile.setLastName(driverRegistration.getLastName());
        driverRepository.save(driverProfile);

        return ResponseEntity.ok(DriverInfo.builder()
                .firstName(driverProfile.getFirstName())
                .lastName(driverProfile.getLastName())
                .licenseNumber(driverProfile.getLicenseNumber())
                .build());
    }

    @Override
    public ResponseEntity<String> deleteDriver(String licenseNumber) {
        Driver driver = driverRepository.findByLicenseNumber(licenseNumber);

        if (driver == null) {
            throw new DriverNotFoundException("Driver with license number " + licenseNumber + " not found");
        }

        driverRepository.delete(driver);
        return new ResponseEntity<>("Driver with license number " + licenseNumber + " has been deleted", HttpStatus.OK);
    }
}
