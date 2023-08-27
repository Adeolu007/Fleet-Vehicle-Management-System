package com.adeolu.Reservation.Service.repository;

import com.adeolu.Reservation.Service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.vehicle = ?1 and r.date = ?2 and r.startTime = ?3")
    Reservation findByVehicleAndDateAndStartTime(String vehicle, LocalDate date, LocalTime startTime);
    //List<Reservation> findByVehicleAndEndTimeAfter(String vehicle, LocalDateTime endTime);
//    List<Reservation> findByVehicleAndStartTimeBetween(String vehicle, LocalTime startTime, LocalTime endTime);
//
//    Reservation findByDate(LocalDate date);
//
//    List<Reservation> findByVehicleAndDateAndStartTimeAndEndTime(String vehicle, LocalDate date, LocalTime startTime, LocalTime endTime);
//


//    List<Reservation> findByVehicleAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
//            String vehicle, LocalDate date, LocalTime endTime, LocalTime startTime);
//@Query(value = "SELECT * FROM Reservation " +
//        "WHERE vehicle = :vehicle " +
//        "AND date = :date " +
//        "AND startTime <= :endTime " +
//        "AND endTime >= :startTime", nativeQuery = true)
//List<Reservation> findByVehicleAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
//        @Param("vehicle") String vehicle,
//        @Param("date") LocalDate date,
//        @Param("startTime") LocalTime startTime,
//        @Param("endTime") LocalTime endTime
//);
}
