package com.adeolu.Vehicle.Management.Service.Gradle.service.serviceImpl;

//import com.adeolu.Vehicle.Management.Service.Gradle.asyncConfig.RabbitMQProducer;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.DriverInfo;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.EmailDetails;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.RegisteredVehicleDto;
import com.adeolu.Vehicle.Management.Service.Gradle.dto.VehicleRegistration;
import com.adeolu.Vehicle.Management.Service.Gradle.entity.Vehicle;
import com.adeolu.Vehicle.Management.Service.Gradle.exceptions.VehicleNotFoundException;
import com.adeolu.Vehicle.Management.Service.Gradle.exceptions.VehicleRegistrationException;
import com.adeolu.Vehicle.Management.Service.Gradle.repository.VehicleRepository;
import com.adeolu.Vehicle.Management.Service.Gradle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
//    @Autowired
//    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    private KafkaTemplate<String, EmailDetails> kafkaTemplate;
    //    @Autowired
//    private WebClient.Builder webClientBuilder;
    @Autowired
   private WebClient webClient;

    //DriverInfo driverInfo = webClientBuilder.build().get().uri("http://localhost:9475/api/drivers/"+ licenseNumber)

    private DriverInfo retrieveDriver(String licenseNumber) {
        DriverInfo driverInfo = webClient.get().uri("http://localhost:9475/api/drivers/"+ licenseNumber)
          .retrieve()
          .bodyToMono(DriverInfo.class)
          .block();
    return driverInfo;
    }



    @Override
    public ResponseEntity<String> registerNewVehicle(VehicleRegistration vehicleRegistration) {
  DriverInfo driverInfo = retrieveDriver(vehicleRegistration.getDriverLicenseNumber());
  log.info(driverInfo.getEmail());
        log.info(driverInfo.getFirstName());
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
                .email(driverInfo.getEmail())
                .firstName(driverInfo.getFirstName())
                .driverLicenseNumber(driverInfo.getLicenseNumber())
//                .email("rem@gmail.com")
//                .firstName("Donald")
//                .driverLicenseNumber("928376473890")
                .build();

        vehicleRepository.save(newVehicle);
//        EmailDetails emailDetails = new EmailDetails();
//        emailDetails.setRecipient(vehicleRegistration.getEmail());
//        emailDetails.setSubject(" Important Notification - Vehicle Assignment in Emperor Fleet Management");
//        emailDetails.setMessage("Dear Mr " + driverInfo.getFirstName() +  " We are pleased to inform you that you have been allocated a new vehicle, bearing the license plate number" + driverInfo.getLicenseNumber());
////       rabbitMqProducer.sendRegistrationEmailNotification(emailDetails);//rabbit mq publishes notification to the consumer

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(newVehicle.getEmail());
        emailDetails.setSubject("NeoClan Tech Transaction Alert [Credit : ]");
        emailDetails.setMessage("Credit transaction of  has been performed on your account. Your new account balance is " );
    kafkaTemplate.send("notificationTopic", emailDetails);

//        rabbitMQProducer.sendCreditEmailNotification(emailDetails); //email notification published via rabbit mq


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

//    private DriverInfo retrieveDriver(String driverLicenseNumber){
//    DriverInfo driverInfo = webClientBuilder.build().get().uri("http://localhost:9475/api/drivers/"+ driverLicenseNumber)
//          .retrieve()
//          .bodyToMono(DriverInfo.class)
//          .block();
//    return driverInfo;
//    }
//localhost:9475/api/drivers/
    //http://driver-management-service/api/drivers/
}
