package com.adeolu.Driver.Management.Service.repository;

import com.adeolu.Driver.Management.Service.modal.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepository extends MongoRepository<Driver, String> {
    boolean existsByLicenseNumber(String licenseNumber);

    Driver findByLicenseNumber(String licenseNumber);


}
