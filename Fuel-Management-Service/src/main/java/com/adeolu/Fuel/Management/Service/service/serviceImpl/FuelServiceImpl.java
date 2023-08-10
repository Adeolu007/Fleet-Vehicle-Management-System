package com.adeolu.Fuel.Management.Service.service.serviceImpl;

import com.adeolu.Fuel.Management.Service.dto.FuelRecordDto;
import com.adeolu.Fuel.Management.Service.dto.FuelRecordResponse;
import com.adeolu.Fuel.Management.Service.dto.RegisteredVehicleDto;
import com.adeolu.Fuel.Management.Service.entity.FuelRecord;
import com.adeolu.Fuel.Management.Service.entity.OdometerReading;
import com.adeolu.Fuel.Management.Service.exception.FuelRecordsNotFoundException;
import com.adeolu.Fuel.Management.Service.exception.VehicleNotFoundException;
import com.adeolu.Fuel.Management.Service.repository.FuelRecordRepository;
import com.adeolu.Fuel.Management.Service.repository.OdometerReadingRepository;
import com.adeolu.Fuel.Management.Service.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FuelServiceImpl implements FuelService {
    @Autowired
    private FuelRecordRepository fuelRecordRepository;
    @Autowired
    private OdometerReadingRepository odometerReadingRepository;
    @Autowired
    private WebClient webClient;


    @Override
    public ResponseEntity<FuelRecordResponse> addFuelRecord(FuelRecordDto fuelRecordDto) {
        // Check if the vehicle (license plate) exists using vehicle management service
        // Check if the driver exists using driver management service
        Boolean isVehiclePresent = vehicleExist(fuelRecordDto.getLicensePlate());
        if(!isVehiclePresent){
            throw new VehicleNotFoundException("This vehicle does not exist");
        }

        RegisteredVehicleDto vehicleDetails = retrieveVehicle(fuelRecordDto.getLicensePlate());

        double totalCost = fuelRecordDto.getLitersFilled() * fuelRecordDto.getCostPerLiter();

        FuelRecord fuelRecord = new FuelRecord();
        fuelRecord.setTotalCost(totalCost);
        fuelRecord.setCostPerLiter(fuelRecordDto.getCostPerLiter());
        // Set license plate and driver based on logic
        fuelRecord.setLicensePlate(vehicleDetails.getLicensePlate());
        //fuelRecord.setLicensePlate(null);
        // Set driver information based on logic
        fuelRecord.setDriverName(vehicleDetails.getFirstName());
        fuelRecord.setRefuelingDate(fuelRecordDto.getRefuelingDate());
        fuelRecord.setLitersFilled(fuelRecordDto.getLitersFilled());

        fuelRecordRepository.save(fuelRecord);

        FuelRecordResponse savedFuelRecord = FuelRecordResponse.builder()
                .refuelingDate(fuelRecord.getRefuelingDate())
                .costPerLiter(fuelRecord.getCostPerLiter())
                .driverName(fuelRecord.getDriverName())
                .licensePlate(fuelRecord.getLicensePlate())
                .litersFilled(fuelRecord.getLitersFilled())
                .totalCost(totalCost)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFuelRecord);
    }

    @Override
    public ResponseEntity<List<FuelRecordResponse>> getFuelRecordsByLicensePlate(String licensePlate) {
        List<FuelRecord> fuelRecords = fuelRecordRepository.findByLicensePlate(licensePlate);

        if (fuelRecords.isEmpty()) {
            throw new FuelRecordsNotFoundException("No fuel records found for license plate: " + licensePlate);
        }

        List<FuelRecordResponse> allFuelRecordsByLicensePlate = fuelRecords.stream()
                .map(this::convertToFuelRecordResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(allFuelRecordsByLicensePlate);
    }

    private FuelRecordResponse convertToFuelRecordResponse(FuelRecord fuelRecord) {
        return FuelRecordResponse.builder()
                .refuelingDate(fuelRecord.getRefuelingDate())
                .costPerLiter(fuelRecord.getCostPerLiter())
                .driverName(fuelRecord.getDriverName())
                .licensePlate(fuelRecord.getLicensePlate())
                .litersFilled(fuelRecord.getLitersFilled())
                .totalCost(fuelRecord.getTotalCost())
                .build();
    }

    @Override
    public double calculateFuelEfficiency(String licensePlate) {
        // Retrieve all fuel records associated with the provided license plate
        List<FuelRecord> fuelRecords = fuelRecordRepository.findByLicensePlate(licensePlate);

        // If no fuel records are found, return 0.0
        if (fuelRecords.isEmpty()) {
            return 0.0;
        }

        // Calculate the total liters of fuel filled across all fuel records
        double totalLitersFilled = fuelRecords.stream().mapToDouble(FuelRecord::getLitersFilled).sum();

        // Calculate the total distance traveled based on odometer readings
        double totalDistanceTraveled = getTotalDistanceTraveled(licensePlate);

        // If total distance traveled is 0, return 0.0 to avoid division by zero
        if (totalDistanceTraveled == 0.0) {
            return 0.0;
        }

        // Calculate and return the fuel efficiency (distance traveled per liter of fuel)
        return totalDistanceTraveled / totalLitersFilled;
    }

    //    private double getTotalDistanceTraveled(String licensePlate) {
//        // Retrieve all odometer readings associated with the provided license plate
//        List<OdometerReading> odometerReadings = odometerReadingRepository.findByLicensePlateOrderByDate(licensePlate);
//
//        // If no odometer readings are found, return 0.0
//        if (odometerReadings.isEmpty()) {
//            return 0.0;
//        }
//
//        // Calculate total distance traveled based on odometer readings
//        double totalDistance = 0.0;
//        OdometerReading previousReading = odometerReadings.get(0);
//
//        // Iterate through the sorted list of odometer readings
//        for (int i = 1; i < odometerReadings.size(); i++) {
//            OdometerReading currentReading = odometerReadings.get(i);
//            // Calculate the difference between consecutive odometer readings to get distance covered
//            totalDistance += currentReading.getDistance() - previousReading.getDistance();
//            previousReading = currentReading;
//        }
//
//        // Return the calculated total distance traveled
//        return totalDistance;
//    }
    private double getTotalDistanceTraveled(String licensePlate) {
        List<OdometerReading> odometerReadings = odometerReadingRepository.findByLicensePlateOrderByDate(licensePlate);

        if (odometerReadings.isEmpty()) {
            return 0.0;
        }

        return IntStream.range(1, odometerReadings.size())
                .mapToDouble(i -> odometerReadings.get(i).getDistance() - odometerReadings.get(i - 1).getDistance())
                .sum();
    }


    private Boolean vehicleExist(String licensePlate){
        Boolean doesVehicleExist = webClient.get().uri("http://localhost:8093/api/vehicles/exist/"+ licensePlate)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesVehicleExist;
    }

    private Boolean driverExist(String licenseNumber){
        Boolean doesDriverExist = webClient.get().uri("http://localhost:9090/api/drivers/exist/"+ licenseNumber)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesDriverExist;
    }

    private RegisteredVehicleDto retrieveVehicle(String licensePlate){
        RegisteredVehicleDto vehicleInfo = webClient.get().uri("http://localhost:8093/api/vehicles"+ licensePlate)
                .retrieve()
                .bodyToMono(RegisteredVehicleDto.class)
                .block();
        return vehicleInfo;
    }
}
