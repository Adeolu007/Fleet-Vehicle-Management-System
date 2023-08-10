package com.adeolu.Fuel.Management.Service.repository;

import com.adeolu.Fuel.Management.Service.entity.FuelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {
    @Query("select f from FuelRecord f where f.licensePlate = ?1")
    List<FuelRecord> findByLicensePlate(String licensePlate);


}