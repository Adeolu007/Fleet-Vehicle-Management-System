package com.adeolu.Fuel.Management.Service.repository;

import com.adeolu.Fuel.Management.Service.entity.OdometerReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OdometerReadingRepository extends JpaRepository<OdometerReading,Long> {
    @Query("select o from OdometerReading o where o.licensePlate = ?1")
    List<OdometerReading> findByLicensePlateOrderByDate(String licensePlate);

}
