package com.adeolu.Maintenance.and.Repair.Service.service;


import com.adeolu.Maintenance.and.Repair.Service.dto.RepairRecordDto;
import com.adeolu.Maintenance.and.Repair.Service.entity.RepairRecord;
import com.adeolu.Maintenance.and.Repair.Service.exception.RepairRecordNotFoundException;
import com.adeolu.Maintenance.and.Repair.Service.exception.VehicleNotFoundException;
import com.adeolu.Maintenance.and.Repair.Service.repository.RepairRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairRecordService {

    @Autowired
    private RepairRecordRepository repairRecordRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

//    @Autowired
//    private VehicleRepository vehicleRepository;

    public ResponseEntity<RepairRecordDto> createRepairRecord(RepairRecordDto repairRecordDto) {
        // Connect to vehicle Micro-Service to get the vehicle
        Boolean isVehiclePresent = vehicleExist(repairRecordDto.getVehicle());
        if(!isVehiclePresent){
            throw new VehicleNotFoundException("This vehicle does not exist");
        }

        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setVehicle(repairRecordDto.getVehicle());
        repairRecord.setDescription(repairRecordDto.getDescription());
        repairRecord.setRepairDate(repairRecordDto.getRepairDate());
        repairRecord.setCost(repairRecordDto.getCost());
        repairRecordRepository.save(repairRecord);
        return ResponseEntity.ok( RepairRecordDto.builder().vehicle(repairRecord.getVehicle()).description(repairRecord.getDescription())
                .cost(repairRecord.getCost()).repairDate(repairRecord.getRepairDate()).build());
    }

    public ResponseEntity<List<RepairRecordDto>> getAllRepairRecords() {
        List<RepairRecord> allRepairRecord = repairRecordRepository.findAll();
        if (allRepairRecord.isEmpty()) {
            throw new RepairRecordNotFoundException("No repair tasks found");
        }
        List<RepairRecordDto> allRepairRecordDto = allRepairRecord.stream()
                .map(this::convertToRepairRecordDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(allRepairRecordDto);
    }
    private RepairRecordDto convertToRepairRecordDto(RepairRecord repairRecord) {
        return RepairRecordDto.builder().vehicle(repairRecord.getVehicle()).description(repairRecord.getDescription())
                .cost(repairRecord.getCost()).repairDate(repairRecord.getRepairDate()).build();
    }
    public ResponseEntity<RepairRecordDto> getRepairRecord(String licensePlate) {
        if(!repairRecordRepository.existsByVehicle(licensePlate)){
            throw new RepairRecordNotFoundException("Repair record not found for vehicle: " + licensePlate);
        }
        RepairRecord repairRecord = repairRecordRepository.findByVehicle(licensePlate);
        return ResponseEntity.ok( RepairRecordDto.builder().vehicle(licensePlate).repairDate(repairRecord.getRepairDate())
                .cost(repairRecord.getCost()).description(repairRecord.getDescription()).build());
    }

    public ResponseEntity<RepairRecordDto> updateRepairRecord(RepairRecordDto repairRecordDto) {
        if(!repairRecordRepository.existsByVehicle(repairRecordDto.getVehicle())){
            throw new RepairRecordNotFoundException("Repair record not found for vehicle: " + repairRecordDto.getVehicle());
        }
        RepairRecord existingRepairRecord = repairRecordRepository.findByVehicle(repairRecordDto.getVehicle());
        existingRepairRecord.setDescription(repairRecordDto.getDescription());
        existingRepairRecord.setRepairDate(repairRecordDto.getRepairDate());
        existingRepairRecord.setDescription(repairRecordDto.getDescription());
        repairRecordRepository.save(existingRepairRecord);
        return ResponseEntity.ok( RepairRecordDto.builder().vehicle(repairRecordDto.getVehicle()).repairDate(repairRecordDto.getRepairDate())
                .cost(repairRecordDto.getCost()).description(repairRecordDto.getDescription()).build());
    }

    public ResponseEntity<String> deleteRepairRecord(String licensePlate) {
        if (!repairRecordRepository.existsByVehicle(licensePlate)) {
            throw new RepairRecordNotFoundException("Repair record not found for vehicle: " + licensePlate);
        }
        repairRecordRepository.deleteByVehicle(licensePlate);
        return ResponseEntity.ok(licensePlate + " has been deleted");
    }

    private Boolean vehicleExist(String licensePlate){
        Boolean doesVehicleExist = webClientBuilder.build().get().uri("http://vehicle-management-service/api/vehicles/exist/"+ licensePlate)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesVehicleExist;
    }
}
