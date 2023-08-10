package com.adeolu.Driver.Management.Service.Gradle.exceptions;

public class DriversNotFoundException extends RuntimeException {
    public DriversNotFoundException(String message) {
        super(message);
    }
}

