package com.adeolu.Notification.Management.Service.modal;

//import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "email_service")
public class EmailEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)

//    private Long id;
    private String message;
    private String subject;
    private String attachment;
    @CreatedDate
    private LocalDateTime created;
}
