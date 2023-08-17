package com.adeolu.Vehicle.Tracking.Service.service;

import com.adeolu.Vehicle.Tracking.Service.entity.Vehicle;
import com.adeolu.Vehicle.Tracking.Service.handler.VehicleWebSocketHandler;
import com.adeolu.Vehicle.Tracking.Service.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleWebSocketHandler webSocketHandler;

    public void updateVehicleLocation(String vehicleId, double latitude, double longitude) {
        Vehicle vehicle = vehicleRepository.findByVehicleLicenseNumber(vehicleId);
        if (vehicle != null) {
            vehicle.setLatitude(latitude);
            vehicle.setLongitude(longitude);
            vehicleRepository.save(vehicle);
            webSocketHandler.sendVehicleLocationUpdate(vehicleId, latitude, longitude);
        }
    }
}
