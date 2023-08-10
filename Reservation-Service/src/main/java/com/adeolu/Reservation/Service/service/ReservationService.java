package com.adeolu.Reservation.Service.service;


import com.adeolu.Reservation.Service.dto.ReservationDto;
import com.adeolu.Reservation.Service.entity.Reservation;
import com.adeolu.Reservation.Service.exception.ReservationConflictException;
import com.adeolu.Reservation.Service.exception.ReservationNotFoundException;
import com.adeolu.Reservation.Service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public ResponseEntity<ReservationDto> createReservation(ReservationDto reservationDto) {
        // Validate if the vehicle is available during the specified time
//        Vehicle vehicle = vehicleRepository.findById(reservationDto.getVehicleId())
//                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        String vehicle ="";

        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();

        List<Reservation> conflictingReservations = reservationRepository.findByVehicleAndStartTimeBetween(vehicle, startTime, endTime);
        if (!conflictingReservations.isEmpty()) {
            throw new ReservationConflictException("Vehicle is already reserved during the specified time");
        }

        Reservation reservation = new Reservation();
        reservation.setVehicle(vehicle);
        //  reservation.setDriver(driverRepository.findById(reservationDto.getDriverId())
        //         .orElseThrow(() -> new DriverNotFoundException("Driver not found")));
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservationRepository.save(reservation);
        return ResponseEntity.ok(ReservationDto.builder().startTime(reservation.getStartTime()).endTime(reservation.getEndTime())
                .driverId(reservationDto.getDriverId()).vehicleId(reservationDto.getVehicleId()).build());
    }

    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationDto> reservationDtos = reservations.stream()
                .map(this::convertToReservationDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservationDtos);
    }

    private ReservationDto convertToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        //reservationDto.setVehicleId(reservation.getVehicle());
        // reservationDto.setDriverId(reservation.getDriver().getId());
        reservationDto.setStartTime(reservation.getStartTime());
        reservationDto.setEndTime(reservation.getEndTime());
        return reservationDto;
    }
    public ResponseEntity<ReservationDto> updateReservation(Long reservationId, ReservationDto reservationDto) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        // Update the reservation details
        //  existingReservation.setDriver(driverRepository.findById(reservationDto.getDriverId())
        //        .orElseThrow(() -> new DriverNotFoundException("Driver not found")));
        existingReservation.setStartTime(reservationDto.getStartTime());
        existingReservation.setEndTime(reservationDto.getEndTime());

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
}
