package com.adeolu.Reservation.Service.exception;

public class ReservationNotFoundException extends RuntimeException{

    //custom exception
    public ReservationNotFoundException(String message){
        super(message);
    }
}
