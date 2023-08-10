package com.adeolu.Driver.Management.Service.Gradle.exceptions;

public class DriverAlreadyExistsException extends RuntimeException {
    public DriverAlreadyExistsException(String message) {
        super(message);
    }
}
