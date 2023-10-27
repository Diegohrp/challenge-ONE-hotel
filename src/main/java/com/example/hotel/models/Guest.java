package com.example.hotel.models;

import com.example.hotel.utils.validate.Validate;

import java.time.LocalDate;
import java.time.Period;


public class Guest {
    private String name, lastName, nationality, phone;
    private LocalDate birthdate;
    private long reservationId;

    public void setName(String name){
        boolean isValid = Validate.validateNonEmpty(name, 200, 3);
        if (isValid) {
            this.name = name;
        } else {
            this.name = null;
        }

    }

    public void setLastName(String lastName){
        boolean isValid = Validate.validateNonEmpty(lastName, 250, 3);
        if (isValid) {
            this.lastName = lastName;
        } else {
            this.lastName = null;
        }

    }

    public void setBirthdate(LocalDate birthdate){
        if (birthdate != null) {
            LocalDate today = LocalDate.now();
            int age = Period.between(birthdate, today).getYears();
            //Adjust the age if their birthday hasn't happened
            if (birthdate.plusYears(age).isAfter(today)) {
                age--;
            }
            if (age >= 18) {
                this.birthdate = birthdate;

            } else {
                this.birthdate = null;
            }
        }
    }

    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public void setPhone(String phone){
        boolean isValid = Validate.validatePhone(phone);
        if (isValid) {
            this.phone = phone;
        } else {
            this.phone = null;
        }

    }

    public void setReservationId(long id){
        this.reservationId = id;
    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    public String getNationality(){
        return nationality;
    }

    public String getPhone(){
        return phone;
    }

    public LocalDate getBirthdate(){
        return birthdate;
    }

    public long getReservationId(){
        return reservationId;
    }
}
