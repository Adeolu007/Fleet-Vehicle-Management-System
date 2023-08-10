package com.adeolu.Maintenance.and.Repair.Service.repository;

import com.adeolu.Maintenance.and.Repair.Service.entity.MaintenanceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
    @Query("select m from MaintenanceTask m where m.vehicle = ?1")
    MaintenanceTask findByVehicle(String vehicle);

    @Query("select (count(m) > 0) from MaintenanceTask m where m.vehicle = ?1")
    boolean existsByVehicle(String vehicle);

    @Query("select m from MaintenanceTask m where m.vehicle = ?1")
    MaintenanceTask deleteByVehicle(String vehicle);


}
