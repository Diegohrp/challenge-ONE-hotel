package com.example.hotel.enums;

public enum PaymentType {

    CASH("Efectivo","cash-icon.png"),
    DEBIT("Tarjeta de débito","debit-icon.png"),
    CREDIT("Tarjeta de crédito","credit-icon.png"),
    PAYPAL("Paypal","paypal-icon.png");

    private final String text;
    private final String icon;

   PaymentType(String text, String icon){
        this.text = text;
        this.icon = icon;
   }

    public String getText(){
        return text;
    }

    public String getIcon(){
        return icon;
    }
}
