package com.example.hotel.models;

import com.example.hotel.enums.PaymentType;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation {

    private LocalDate checkIn, checkOut;
    private Double total;
    private String payment;


    public Reservation(){
        this.total = 0.0;
        this.payment = PaymentType.CASH.getText();
    }

    //Validates that the check-in or check-out date is after today or equal
    private boolean dateAfterToday(LocalDate date){
        LocalDate currentDate = LocalDate.now();
        return date.isAfter(currentDate) || date.isEqual(currentDate);
    }

    public boolean setCheckIn(LocalDate checkIn){
        boolean isValid = this.dateAfterToday(checkIn);
        //checkIn must be before checkOut
        if (this.checkOut != null) isValid = isValid && checkIn.isBefore(this.checkOut);

        if (isValid) {
            this.checkIn = checkIn;
        } else {
            this.checkIn = null;
        }

        this.calcTotal();
        return isValid;
    }

    public boolean setCheckOut(LocalDate checkOut){
        boolean isValid = this.dateAfterToday(checkOut);
        //checkOut must be after checkIn
        if (this.checkIn != null) isValid = isValid && checkOut.isAfter(this.checkIn);

        if (isValid) {
            this.checkOut = checkOut;
        } else {
            this.checkOut = null;
        }

        this.calcTotal();
        return isValid;
    }

    //Calculates the total price based on a daily rate and the number of days
    public void calcTotal(){
        //the total value is calculated only when both dates are assigned
        //if both dates are assigned it's because they're correct
        if (this.checkIn != null && this.checkOut != null) {
            double rate = 723.75;
            double numOfDays = DAYS.between(this.checkIn, this.checkOut);
            this.total = rate * numOfDays;
        } else {
            this.total = 0.0;
        }
    }

    public void setPayment(String payment){
        this.payment = payment;
    }

    public Double getTotal(){
        return this.total;
    }

    public LocalDate getCheckIn(){
        return checkIn;
    }

    public LocalDate getCheckOut(){
        return checkOut;
    }

    public String getPayment(){
        return payment;
    }
}
