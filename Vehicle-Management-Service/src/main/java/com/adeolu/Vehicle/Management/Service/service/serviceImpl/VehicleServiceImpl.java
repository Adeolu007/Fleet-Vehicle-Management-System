package com.adeolu.Vehicle.Management.Service.service.serviceImpl;

import com.adeolu.Vehicle.Management.Service.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.dto.VehicleRegistration;
import com.adeolu.Vehicle.Management.Service.entity.Vehicle;
import com.adeolu.Vehicle.Management.Service.exceptions.VehicleNotFoundException;
import com.adeolu.Vehicle.Management.Service.exceptions.VehicleRegistrationException;
import com.adeolu.Vehicle.Management.Service.repository.VehicleRepository;
import com.adeolu.Vehicle.Management.Service.service.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
   @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration) {
//        if (vehicleRepository.existsByLicensePlate(vehicleRegistration.getLicensePlate())) {
//            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
//                    .body("This vehicle is already registered");
//        }
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

        RegisteredVehicleDto registeredVehicleDto= RegisteredVehicleDto.builder() .licensePlate(vehicle.getLicensePlate())
                .acquisitionDate(vehicle.getAcquisitionDate())
                .model(vehicle.getModel())
                .make(vehicle.getMake())
                .year(vehicle.getYear())
                .fuelCapacity(vehicle.getFuelCapacity())
                .description(vehicle.getDescription())
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
                .description(vehicle.getDescription())
                .build();

        return ResponseEntity.ok(registeredVehicleDto);
    }


    }


