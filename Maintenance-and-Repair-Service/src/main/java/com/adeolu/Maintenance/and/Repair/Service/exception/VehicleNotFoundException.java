package com.adeolu.Maintenance.and.Repair.Service.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
    public static class MaintenanceTaskNotFoundException extends RuntimeException {
        public MaintenanceTaskNotFoundException(String message) {
            super(message);
        }}
}
