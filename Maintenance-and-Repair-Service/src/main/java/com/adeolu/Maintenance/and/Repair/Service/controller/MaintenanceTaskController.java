package com.adeolu.Maintenance.and.Repair.Service.controller;

import com.adeolu.Maintenance.and.Repair.Service.dto.MaintenanceTaskDto;
import com.adeolu.Maintenance.and.Repair.Service.service.MaintenanceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceTaskController {

    @Autowired
    private MaintenanceTaskService maintenanceTaskService;

    @PostMapping("/create")
    public ResponseEntity<MaintenanceTaskDto> createMaintenanceTask(@RequestBody MaintenanceTaskDto maintenanceTaskDto) {
        return maintenanceTaskService.createMaintenanceTask(maintenanceTaskDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceTaskDto>> getAllMaintenanceTasks() {
        return maintenanceTaskService.getAllMaintenanceTasks();
    }

    @GetMapping("/getByVehicle/{vehicle}")
    public ResponseEntity<MaintenanceTaskDto> getMaintenanceTaskByVehicle(@PathVariable String vehicle) {
        return maintenanceTaskService.getMaintenanceTaskByVehicle(vehicle);
    }

    @PutMapping("/update")
    public ResponseEntity<MaintenanceTaskDto> updateMaintenanceTask(@RequestBody MaintenanceTaskDto maintenanceTaskDto) {
        return maintenanceTaskService.updateMaintenanceTask(maintenanceTaskDto);
    }

    @DeleteMapping("/delete/{vehicle}")
    public ResponseEntity<String> deleteMaintenanceTask(@PathVariable String vehicle) {
        return maintenanceTaskService.deleteMaintenanceTask(vehicle);
    }
}
