package com.adeolu.Reservation.Service.repository;

import com.adeolu.Reservation.Service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByVehicleAndEndTimeAfter(String vehicle, LocalDateTime endTime);
    List<Reservation> findByVehicleAndStartTimeBetween(String vehicle, LocalDateTime startTime, LocalDateTime endTime);
}
