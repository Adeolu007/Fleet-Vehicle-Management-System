package com.adeolu.Vehicle.Management.Service.Gradle.contoller;

import com.adeolu.Vehicle.Management.Service.Gradle.configCommunication.RabbitMQProducer;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.DriverInfo;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.VehicleRegistration;
import com.adeolu.Vehicle.Management.Service.Gradle.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
//@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    //private final RabbitMQProducer producer;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
       // this.producer = producer;
    }

//@GetMapping("/publish")
//    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
//        producer.sendMessage(message);
//        return ResponseEntity.ok("Message sent to rabbitMq");
//    }





    @PostMapping("/register")
    public ResponseEntity<String> registerVehicle(@RequestBody VehicleRegistration vehicleRegistration) {
        return vehicleService.registerNewVehicle(vehicleRegistration);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<RegisteredVehicleDto> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return vehicleService.findByLicensePlate(licensePlate);
    }

    //not working

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String licensePlate) {
        return vehicleService.deleteVehicle(licensePlate);
    }

    @PutMapping("/update")
    public ResponseEntity<RegisteredVehicleDto> updateVehicle(@RequestBody VehicleRegistration vehicleRegistration) {
        return vehicleService.updateVehicle(vehicleRegistration);
    }

    @GetMapping("/exist/{licensePlate}")
    public ResponseEntity<Boolean> doesVehicleExist(@PathVariable String licensePlate){
        return vehicleService.doesVehicleExist(licensePlate);
    }
}
