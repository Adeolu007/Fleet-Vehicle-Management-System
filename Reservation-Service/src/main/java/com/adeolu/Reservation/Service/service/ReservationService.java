package com.adeolu.Reservation.Service.service;


import com.adeolu.Reservation.Service.dto.RegisteredVehicleDto;
import com.adeolu.Reservation.Service.dto.ReservationDto;
import com.adeolu.Reservation.Service.entity.Reservation;
import com.adeolu.Reservation.Service.exception.CustomReservationException;
import com.adeolu.Reservation.Service.exception.ReservationConflictException;
import com.adeolu.Reservation.Service.exception.ReservationNotFoundException;
import com.adeolu.Reservation.Service.exception.VehicleNotFoundException;
import com.adeolu.Reservation.Service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private WebClient webClient;

    //not tested this
    public ResponseEntity<ReservationDto> createReservation(ReservationDto reservationDto) {
//        if (isVehicleAvailable(reservationDto.getVehicle(), reservationDto.getStartTime(), reservationDto.getEndTime(), reservationDto.getDate())){
//            // Save the reservation to the database
//            Reservation savedReservation = Reservation.builder().driver(reservationDto.getDriver()).vehicle(reservationDto.getVehicle())
//                    .startTime(reservationDto.getStartTime()).endTime(reservationDto.getEndTime()).purpose(reservationDto.getPurpose())
//
//            .build();
        if (!vehicleExist(reservationDto.getVehicle()))
            throw new VehicleNotFoundException("This vehicle does not exist");
        Reservation existingReservation = reservationRepository.findByVehicleAndDateAndStartTime(
                reservationDto.getVehicle(), reservationDto.getDate(), reservationDto.getStartTime());

        if (existingReservation != null) {
            // A reservation for the same vehicle, date, and startTime already exists
            throw new CustomReservationException("A reservation for the same vehicle, date, and startTime already exists.");
        } else {
            // Create a new reservation
            Reservation reservation = new Reservation();
            reservation.setVehicle(reservationDto.getVehicle());
            reservation.setDriver(reservationDto.getDriver());
            reservation.setDate(reservationDto.getDate());
            reservation.setStartTime(reservationDto.getStartTime());
            reservation.setEndTime(reservationDto.getEndTime());
            reservation.setPurpose(reservationDto.getPurpose());

            // Save the new reservation to the repository
             reservationRepository.save(reservation);

            return ResponseEntity.ok(ReservationDto.builder().vehicle(reservation.getVehicle()).driver(reservation.getDriver())
                    .date(reservation.getDate()).startTime(reservation.getStartTime()).endTime(reservation.getEndTime())
                    .purpose(reservation.getPurpose()).build());
        }
//        Reservation reservation = reservationRepository.find
//
//                    reservationRepository.save(savedReservation);
//
//            return ResponseEntity.ok(ReservationDto.builder().vehicle(savedReservation.getVehicle()).driver(savedReservation.getDriver())
//                    .date(savedReservation.getDate()).startTime(savedReservation.getStartTime()).endTime(savedReservation.getEndTime())
//                    .purpose(savedReservation.getPurpose()).build());
//        } else {
//            throw new ReservationConflictException("Vehicle is not available for the specified time period");
//        }
    }
//    private boolean isVehicleAvailable(String vehicle, LocalTime startTime, LocalTime endTime, LocalDate date) {
////        // Check if there are any overlapping reservations for the specified vehicle and time period
////        List<Reservation> overlappingReservations = reservationRepository
////                .findByVehicleAndDateAndStartTimeAndEndTime(vehicle, date, startTime, endTime);
////
////        // If there are no overlapping reservations, the vehicle is available
////        return overlappingReservations.isEmpty();
//
//        List<Reservation> overlappingReservations = reservationRepository
//                .findByVehicleAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
//                        vehicle, date, endTime, startTime);
//
//        // If there are no overlapping reservations, the vehicle is available
//        return overlappingReservations.isEmpty();
//    }
    //.....................................................................................................................
//    public List<Reservation> getReservationsByVehicleId(String VehicleLicenseNumber) {
//        if(reservationRepository.)
//        return reservationRepository.findByVehicleIdAndStartTimeBeforeAndEndTimeAfter(vehicleId, LocalDateTime.now(), LocalDateTime.now());
//    }
//
//
//
//
//
//

//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        if(reservationRepository.findAll()==null) return null;
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationDto> reservationDtos = reservations.stream()
                .map(this::convertToReservationDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservationDtos);
    }

    private ReservationDto convertToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setVehicle(reservation.getVehicle());
         reservationDto.setDriver(reservation.getDriver());
        reservationDto.setStartTime(reservation.getStartTime());
        reservationDto.setEndTime(reservation.getEndTime());
        reservationDto.setPurpose(reservation.getPurpose());
        return reservationDto;
    }
    public ResponseEntity<ReservationDto> updateReservation(Long reservationId, ReservationDto reservationDto) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        // Update the reservation details
        //  existingReservation.setDriver(driverRepository.findById(reservationDto.getDriverId())
        //        .orElseThrow(() -> new DriverNotFoundException("Driver not found")));
        existingReservation.setVehicle(reservationDto.getVehicle());
        existingReservation.setDriver(reservationDto.getDriver());
        existingReservation.setStartTime(reservationDto.getStartTime());
        existingReservation.setEndTime(reservationDto.getEndTime());
        existingReservation.setPurpose(reservationDto.getPurpose());

        Reservation updatedReservation = reservationRepository.save(existingReservation);
        ReservationDto updatedReservationDto = convertToReservationDto(updatedReservation);

        return ResponseEntity.ok(updatedReservationDto);
    }
    public ResponseEntity<String> deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        reservationRepository.delete(reservation);

        return ResponseEntity.ok("Reservation deleted successfully");
    }

    public ResponseEntity<ReservationDto> getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        ReservationDto reservationDto = convertToReservationDto(reservation);

        return ResponseEntity.ok(reservationDto);
    }

    private Boolean vehicleExist(String licensePlate){
        Boolean doesVehicleExist = webClient.get().uri("http://localhost:4545/api/vehicles/exist/"+ licensePlate)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesVehicleExist;
    }
    //"http://vehicle-management-service/api/vehicles/exist/"
    private Boolean driverExist(String licenseNumber){
        Boolean doesDriverExist = webClient.get().uri("http://localhost:9475/api/drivers/exist/"+ licenseNumber)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return doesDriverExist;
    }

    //http://driver-management-service/api/drivers/exist/

    //    private RegisteredVehicleDto retrieveVehicle(String licensePlate){
//        RegisteredVehicleDto vehicleInfo = webClientBuilder.build().get().uri("http://localhost:4545/api/vehicles/"+ licensePlate)
//                .retrieve()
//                .bodyToMono(RegisteredVehicleDto.class)
//                .block();
//        return vehicleInfo;
//    }
    private RegisteredVehicleDto retrieveVehicle(String licensePlate) {
        RegisteredVehicleDto vehicleInfo = webClient.get().uri("http://localhost:4545/api/vehicles/"+ licensePlate)
                .retrieve()
                .bodyToMono(RegisteredVehicleDto.class)
                .block();
        return vehicleInfo;
    }
}
