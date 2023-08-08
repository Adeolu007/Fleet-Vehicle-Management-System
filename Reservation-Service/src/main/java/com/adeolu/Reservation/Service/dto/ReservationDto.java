package com.adeolu.Reservation.Service.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private Long vehicleId;
    private Long driverId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
