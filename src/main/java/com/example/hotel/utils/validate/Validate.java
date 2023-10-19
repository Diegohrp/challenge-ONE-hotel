package com.example.hotel.utils.validate;

public class Validate {

    public static boolean validateNonEmpty(String field, int maxLength, int minLength){
        return field != null && !field.equals("") &&
            (field.length() < maxLength && field.length() >= minLength);
    }
}
