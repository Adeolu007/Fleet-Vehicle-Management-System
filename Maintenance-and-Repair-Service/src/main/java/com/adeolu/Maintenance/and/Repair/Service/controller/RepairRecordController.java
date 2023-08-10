package com.adeolu.Maintenance.and.Repair.Service.controller;


import com.adeolu.Maintenance.and.Repair.Service.dto.RepairRecordDto;
import com.adeolu.Maintenance.and.Repair.Service.service.RepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-records")
public class RepairRecordController {

    private final RepairRecordService repairRecordService;

    @Autowired
    public RepairRecordController(RepairRecordService repairRecordService) {
        this.repairRecordService = repairRecordService;
    }

    @PostMapping
    public ResponseEntity<RepairRecordDto> createRepairRecord(@RequestBody RepairRecordDto repairRecordDto) {
        return repairRecordService.createRepairRecord(repairRecordDto);
    }

    @GetMapping
    public ResponseEntity<List<RepairRecordDto>> getAllRepairRecords() {
        return repairRecordService.getAllRepairRecords();
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<RepairRecordDto> getRepairRecord(@PathVariable String licensePlate) {
        return repairRecordService.getRepairRecord(licensePlate);
    }

    @PutMapping
    public ResponseEntity<RepairRecordDto> updateRepairRecord(@RequestBody RepairRecordDto repairRecordDto) {
        return repairRecordService.updateRepairRecord(repairRecordDto);
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<String> deleteRepairRecord(@PathVariable String licensePlate) {
        return repairRecordService.deleteRepairRecord(licensePlate);
    }
}

