package com.adeolu.Reservation.Service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private String vehicle;
    private String driver;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;
}
