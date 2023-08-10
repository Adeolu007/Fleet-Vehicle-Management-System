package com.adeolu.Reservation.Service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
//    @ManyToOne
//    @JoinColumn(name = "vehicle_id")
    private String vehicle;
    //
//    @ManyToOne
//    @JoinColumn(name = "driver_id")
    private String driver;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
