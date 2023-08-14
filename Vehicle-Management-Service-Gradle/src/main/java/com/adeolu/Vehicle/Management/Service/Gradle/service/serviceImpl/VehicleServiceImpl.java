package com.adeolu.Vehicle.Management.Service.Gradle.service.serviceImpl;

import com.adeolu.Vehicle.Management.Service.Gradle.dto.DriverInfo;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.VehicleRegistration;
import com.adeolu.Vehicle.Management.Service.Gradle.entity.Vehicle;
import com.adeolu.Vehicle.Management.Service.Gradle.exceptions.VehicleNotFoundException;
import com.adeolu.Vehicle.Management.Service.Gradle.exceptions.VehicleRegistrationException;
import com.adeolu.Vehicle.Management.Service.Gradle.repository.VehicleRepository;
import com.adeolu.Vehicle.Management.Service.Gradle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration) {
        //        if (vehicleRepository.existsByLicensePlate(vehicleRegistration.getLicensePlate())) {
//            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
//                    .body("This vehicle is already registered");
//        }
        DriverInfo driverInfo = retrieveDriver(vehicleRegistration.getDriverLicenseNumber());
        log.info(driverInfo.getFirstName());
        log.info(driverInfo.getLastName());
        log.info(driverInfo.getLicenseNumber());
        if (vehicleRepository.existsByLicensePlate(vehicleRegistration.getLicensePlate())) {
            throw new VehicleRegistrationException("This vehicle is already registered");}

        Vehicle newVehicle = Vehicle.builder()
                .licensePlate(vehicleRegistration.getLicensePlate())
                .acquisitionDate(vehicleRegistration.getAcquisitionDate())
                .model(vehicleRegistration.getModel())
                .make(vehicleRegistration.getMake())
                .year(vehicleRegistration.getYear())
                .fuelCapacity(vehicleRegistration.getFuelCapacity())
                .description(vehicleRegistration.getDescription())
        //        .firstName(vehicleRegistration.getFirstName())
          //      .driverLicenseNumber(vehicleRegistration.getDriverLicenseNumber())
                .firstName(driverInfo.getFirstName())
                .driverLicenseNumber(driverInfo.getLicenseNumber())
                .build();

        vehicleRepository.save(newVehicle);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Vehicle with License Plate " + vehicleRegistration.getLicensePlate() + " has been registered");

    }

    @Override
    public ResponseEntity<RegisteredVehicleDto> findByLicensePlate(String licensePlate) {
        if(!vehicleRepository.existsByLicensePlate(licensePlate)){
            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
  //      DriverInfo driverInfo = retrieveDriver(vehicleRegistration.getDriverLicenseNumber());
        RegisteredVehicleDto registeredVehicleDto= RegisteredVehicleDto.builder().licensePlate(vehicle.getLicensePlate())
                .acquisitionDate(vehicle.getAcquisitionDate())
                .model(vehicle.getModel())
                .make(vehicle.getMake())
                .year(vehicle.getYear())
                .fuelCapacity(vehicle.getFuelCapacity())
                .description(vehicle.getDescription())
                .firstName(vehicle.getFirstName())
                .driverLicenseNumber(vehicle.getDriverLicenseNumber())
                .build();
        return ResponseEntity.ok(registeredVehicleDto);

    }

    @Override
    public ResponseEntity<String> deleteVehicle(String licensePlate) {
        if (!vehicleRepository.existsByLicensePlate(licensePlate)) {
            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        vehicleRepository.deleteByLicensePlate(licensePlate);
        return ResponseEntity.ok("Vehicle with License Plate " + licensePlate + " has been deleted");


    }

    @Override
    public ResponseEntity<RegisteredVehicleDto> updateVehicle(VehicleRegistration vehicleRegistration) {
        if (!vehicleRepository.existsByLicensePlate(vehicleRegistration.getLicensePlate())) {
            throw new VehicleNotFoundException("This vehicle does not exist");
        }
        Vehicle vehicle = vehicleRepository.findByLicensePlate(vehicleRegistration.getLicensePlate());

        RegisteredVehicleDto registeredVehicleDto = RegisteredVehicleDto.builder()
                .licensePlate(vehicleRegistration.getLicensePlate())
                .make(vehicleRegistration.getMake())
                .model(vehicleRegistration.getModel())
                .fuelCapacity(vehicleRegistration.getFuelCapacity())
                .year(vehicleRegistration.getYear())
                .acquisitionDate(vehicleRegistration.getAcquisitionDate())
                .description(vehicleRegistration.getDescription())
        //        .firstName(vehicleRegistration.getFirstName())
                .driverLicenseNumber(vehicleRegistration.getDriverLicenseNumber())
                .build();

        return ResponseEntity.ok(registeredVehicleDto);
    }

    @Override
    public ResponseEntity<Boolean> doesVehicleExist(String licensePlate) {
        return ResponseEntity.ok(vehicleRepository.existsByLicensePlate(licensePlate));
    }

    private DriverInfo retrieveDriver(String driverLicenseNumber){
    DriverInfo driverInfo = webClientBuilder.build().get().uri("http://driver-management-service/api/drivers/"+ driverLicenseNumber)
          .retrieve()
          .bodyToMono(DriverInfo.class)
          .block();
    return driverInfo;
    }
}
