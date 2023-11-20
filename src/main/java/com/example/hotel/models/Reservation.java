package com.example.hotel.models;

import com.example.hotel.enums.PaymentType;
import javafx.beans.property.*;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation {
    private long id;
    private final ObjectProperty<LocalDate> checkIn, checkOut;
    private final DoubleProperty total;
    private String payment;


    public Reservation(){
        this.total = new SimpleDoubleProperty(0.0);
        this.payment = PaymentType.CASH.getText();
        this.checkIn = new SimpleObjectProperty<>();
        this.checkOut = new SimpleObjectProperty<>();
    }

    public Reservation(long id, LocalDate checkIn, LocalDate checkOut, Double total,
                       String payment){
        this.id = id;
        this.checkIn = new SimpleObjectProperty<>(checkIn);
        this.checkOut = new SimpleObjectProperty<>(checkOut);
        this.total = new SimpleDoubleProperty(total);
        this.payment = payment;
    }

    //Validates that the check-in or check-out date is after today or equal
    private boolean dateAfterToday(LocalDate date){
        LocalDate currentDate = LocalDate.now();
        return date.isAfter(currentDate) || date.isEqual(currentDate);
    }

    public boolean setCheckIn(LocalDate checkIn){
        if (checkIn != null) {
            boolean isValid = this.dateAfterToday(checkIn);
            //checkIn must be before checkOut
            if (this.checkOut.get() != null)
                isValid = isValid && checkIn.isBefore(this.checkOut.get());

            this.checkIn.set(checkIn);

            return isValid;
        }
        return false;
    }

    public boolean setCheckOut(LocalDate checkOut){
        if (checkOut != null) {
            boolean isValid = this.dateAfterToday(checkOut);
            //checkOut must be after checkIn
            if (this.checkIn.get() != null)
                isValid = isValid && checkOut.isAfter(this.checkIn.get());

            this.checkOut.set(checkOut);
            return isValid;
        }
        return false;
    }

    //Calculates the total price based on a daily rate and the number of days
    public void calcTotal(){
        //the total value is calculated only when both dates are assigned
        //if both dates are assigned it's because they're correct
        if (this.checkIn.get() != null && this.checkOut.get() != null) {
            double rate = 723.75;
            double numOfDays = DAYS.between(this.checkIn.get(), this.checkOut.get());
            this.total.set(rate * numOfDays);

            if (this.total.get() < 0.0) {
                this.total.set(0.0);
            }
        }
    }


    public void setPayment(String payment){
        this.payment = payment;
    }

    public Double getTotal(){
        return this.total.get();
    }

    public DoubleProperty totalProperty(){
        return this.total;
    }

    public LocalDate getCheckIn(){
        return checkIn.get();
    }

    public LocalDate getCheckOut(){
        return checkOut.get();
    }

    public String getPayment(){
        return payment;
    }

    public long getId(){
        return id;
    }

}
