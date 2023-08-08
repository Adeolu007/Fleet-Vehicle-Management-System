package com.adeolu.Driver.Management.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DriverRegistration {
    private String firstName;
    private String lastName;
    private String licenseNumber;
}
