package com.example.hotel.utils.validate;

public class Validate {

    public static boolean validateNonEmpty(String field, int maxLength, int minLength){
        return field != null && !field.equals("") &&
            (field.length() < maxLength && field.length() >= minLength);
    }

    public static boolean validatePhone(String phone){
        return phone.matches("^\\d{10}$");
    }

    public static boolean isNumericLong(String num){
        return num.matches("^\\d+$");
    }
}
