package com.adeolu.Maintenance.and.Repair.Service.repository;

import com.adeolu.Maintenance.and.Repair.Service.entity.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepairRecordRepository extends JpaRepository<RepairRecord, Long> {
    @Query("select r from RepairRecord r where r.vehicle = ?1")
    RepairRecord findByVehicle(String vehicle);

    @Query("select (count(r) > 0) from RepairRecord r where r.vehicle = ?1")
    boolean existsByVehicle(String vehicle);

    @Transactional
    @Modifying
    @Query("delete from RepairRecord r where r.vehicle = ?1")
    int deleteByVehicle(String vehicle);


}
