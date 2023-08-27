package com.adeolu.Driver.Management.Service.Gradle.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Builder
@Table(name = "driver")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Driver {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "License number is required")
    @Pattern(regexp = "[0-9]{10}", message = "National identity number must be 11 digits")
    private String licenseNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
    @Pattern(regexp = "[0-9]{11}", message = "National identity number must be 11 digits")
    private String nationalIdentityNumber;
    @NotNull(message = "Gender is required")
    private String gender;
    @NotNull(message = "Marital status is required")
    private String maritalStatus;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @Pattern(regexp = "[0-9]{11}", message = "Phone number must be 11 digits")
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
}
