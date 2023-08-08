package com.adeolu.Reservation.Service.controller;


import com.adeolu.Reservation.Service.dto.ReservationDto;
import com.adeolu.Reservation.Service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.createReservation(reservationDto);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> updateReservation(
            @PathVariable Long reservationId,
            @RequestBody ReservationDto reservationDto) {
        return reservationService.updateReservation(reservationId, reservationDto);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        return reservationService.deleteReservation(reservationId);
    }
}
