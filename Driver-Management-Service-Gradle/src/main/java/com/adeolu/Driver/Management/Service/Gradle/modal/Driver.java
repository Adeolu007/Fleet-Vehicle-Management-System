package com.adeolu.Driver.Management.Service.Gradle.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Builder
@Table(name = "driver")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Driver {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private Date hireDate;
    private String nationalIdentityNumber;
    private String gender;
    private String maritalStatus;
    private String email;
    private String address;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
}
