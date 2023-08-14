package com.adeolu.Maintenance.and.Repair.Service.service;


import com.adeolu.Maintenance.and.Repair.Service.dto.MaintenanceTaskDto;
import com.adeolu.Maintenance.and.Repair.Service.dto.RegisteredVehicleDto;
import com.adeolu.Maintenance.and.Repair.Service.entity.MaintenanceTask;
import com.adeolu.Maintenance.and.Repair.Service.exception.NoMaintenanceTasksFoundException;
import com.adeolu.Maintenance.and.Repair.Service.exception.VehicleNotFoundException;
import com.adeolu.Maintenance.and.Repair.Service.repository.MaintenanceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceTaskService {
    @Autowired
    private MaintenanceTaskRepository maintenanceTaskRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
//
//    @Autowired
//    private VehicleRepository vehicleRepository;

    public ResponseEntity<MaintenanceTaskDto> createMaintenanceTask(MaintenanceTaskDto maintenanceTaskDto) {
        //connect to vehicle Micro-Service to get the vehicle
//        Vehicle vehicle = vehicleRepository.findByLicensePlate(maintenanceTaskDto.getLicensePlate())
//                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

        Boolean isVehiclePresent = vehicleExist(maintenanceTaskDto.getVehicle());
        if(!isVehiclePresent){
            throw new VehicleNotFoundException("This vehicle does not exist");
        }

        RegisteredVehicleDto vehicleDetails = retrieveVehicle(maintenanceTaskDto.getVehicle());

        MaintenanceTask maintenanceTask = new MaintenanceTask();
        maintenanceTask.setVehicle(maintenanceTaskDto.getVehicle());
        maintenanceTask.setDescription(maintenanceTaskDto.getDescription());
        maintenanceTask.setScheduledDate(maintenanceTaskDto.getScheduledDate());
        maintenanceTaskRepository.save(maintenanceTask);
        return ResponseEntity.ok(MaintenanceTaskDto.builder().vehicle(maintenanceTask.getVehicle()).description(maintenanceTask.getDescription())
                .scheduledDate(maintenanceTask.getScheduledDate()).build());
    }

    public ResponseEntity<List<MaintenanceTaskDto>> getAllMaintenanceTasks() {
        List<MaintenanceTask> allMaintenanceTasks = maintenanceTaskRepository.findAll();
        if (allMaintenanceTasks.isEmpty()) {
            throw new NoMaintenanceTasksFoundException("No maintenance tasks found");
        }
        List<MaintenanceTaskDto> allMaintenanceTaskDto = allMaintenanceTasks.stream()
                .map(this::convertToMaintenanceTaskDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(allMaintenanceTaskDto);
    }
    private MaintenanceTaskDto convertToMaintenanceTaskDto(MaintenanceTask maintenanceTask) {
        return MaintenanceTaskDto.builder().vehicle(maintenanceTask.getVehicle()).description(maintenanceTask.getDescription())
                .scheduledDate(maintenanceTask.getScheduledDate()).build();
    }

    public ResponseEntity<MaintenanceTaskDto> getMaintenanceTaskByVehicle(String vehicle) {
        if(!maintenanceTaskRepository.existsByVehicle(vehicle)){
            throw new  VehicleNotFoundException.MaintenanceTaskNotFoundException("Maintenance task not found for vehicle: " + vehicle);
        }
        MaintenanceTask maintenanceTask = maintenanceTaskRepository.findByVehicle(vehicle);
        return ResponseEntity.ok( MaintenanceTaskDto.builder().vehicle(maintenanceTask.getVehicle())
                .description(maintenanceTask.getDescription()).scheduledDate(maintenanceTask.getScheduledDate())
                .build());
    }

    public ResponseEntity<MaintenanceTaskDto> updateMaintenanceTask(MaintenanceTaskDto maintenanceTaskDto) {
        if(!maintenanceTaskRepository.existsByVehicle(maintenanceTaskDto.getVehicle())){
            throw new  VehicleNotFoundException.MaintenanceTaskNotFoundException("Maintenance task not found for vehicle: " + maintenanceTaskDto.getVehicle());
        }
        MaintenanceTask existingMaintenanceTask = maintenanceTaskRepository.findByVehicle(maintenanceTaskDto.getVehicle());

        existingMaintenanceTask.setDescription(maintenanceTaskDto.getDescription());
        existingMaintenanceTask.setScheduledDate(maintenanceTaskDto.getScheduledDate());
        existingMaintenanceTask.setVehicle(maintenanceTaskDto.getVehicle());
        maintenanceTaskRepository.save(existingMaintenanceTask);
        return ResponseEntity.ok( MaintenanceTaskDto.builder().vehicle(existingMaintenanceTask.getVehicle())
                .description(existingMaintenanceTask.getDescription()).scheduledDate(existingMaintenanceTask.getScheduledDate())
                .build());
    }

    public ResponseEntity<String> deleteMaintenanceTask(String vehicle) {
        if (!maintenanceTaskRepository.existsByVehicle(vehicle)) {
            throw new VehicleNotFoundException.MaintenanceTaskNotFoundException("Maintenance task not found");
        }
        maintenanceTaskRepository.deleteByVehicle(vehicle);
        return ResponseEntity.ok(vehicle + " has been deleted");
    }

    //////
    private Boolean vehicleExist(String licensePlate){
        Boolean doesVehicleExist = webClientBuilder.build().get().uri("http://vehicle-management-service/api/vehicles/exist/"+ licensePlate)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesVehicleExist;
    }

    private Boolean driverExist(String licenseNumber){
        Boolean doesDriverExist = webClientBuilder.build().get().uri("http://driver-management-service/api/drivers/exist/"+ licenseNumber)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesDriverExist;
    }

    private RegisteredVehicleDto retrieveVehicle(String licensePlate){
        RegisteredVehicleDto vehicleInfo = webClientBuilder.build().get().uri("http://vehicle-management-service/api/vehicles/"+ licensePlate)
                .retrieve()
                .bodyToMono(RegisteredVehicleDto.class)
                .block();
        return vehicleInfo;
    }
}
