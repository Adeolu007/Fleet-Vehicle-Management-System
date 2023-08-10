package com.adeolu.Driver.Management.Service.Gradle.repository;

import com.adeolu.Driver.Management.Service.Gradle.modal.Driver;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverRepository extends JpaRepository<Driver, String> {
    boolean existsByLicenseNumber(String licenseNumber);
    Driver findByLicenseNumber(String licenseNumber);
}
