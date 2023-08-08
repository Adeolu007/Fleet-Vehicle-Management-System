package com.adeolu.Fuel.Management.Service.service;

import com.adeolu.Fuel.Management.Service.dto.FuelRecordDto;
import com.adeolu.Fuel.Management.Service.dto.FuelRecordResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FuelService {
    ResponseEntity<FuelRecordResponse> addFuelRecord(FuelRecordDto fuelRecordDto);
    ResponseEntity<List<FuelRecordResponse>> getFuelRecordsByLicensePlate(String licensePlate);
    double calculateFuelEfficiency(String licensePlate);

}
