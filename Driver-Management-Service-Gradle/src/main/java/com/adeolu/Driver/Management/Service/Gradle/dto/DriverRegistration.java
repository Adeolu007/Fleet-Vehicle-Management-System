package com.adeolu.Driver.Management.Service.Gradle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DriverRegistration {
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
    private Date dateOfBirth;
}
