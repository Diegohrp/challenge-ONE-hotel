package com.example.hotel.models;



public class ReservationNumber {
    /*
    This static variable is used to know if a reservation has been made
    and the following step is to make the register process. In that case, if the
    user closes the app or returns to reservation view, the reservation registered
    in the DB must be deleted, because the complete process couldn't be finished.
    */
    public static long num;
}
