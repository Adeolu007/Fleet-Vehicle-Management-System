package com.adeolu.Driver.Management.Service.Gradle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DriverInfo {
    private String firstName;
    private String lastName;
    private String licenseNumber;
}
