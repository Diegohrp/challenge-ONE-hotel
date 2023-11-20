package com.example.hotel.enums;

public enum Nationality {
    GERMAN("alemán - alemana"),
    ARGENTINIAN("argentino - argentina"),
    BANGLADESHI("bangladesí - bangladesí"),
    BOLIVIAN("boliviano - boliviana"),
    BRAZILIAN("brasileño - brasileña"),
    CHILEAN("chileno - chilena"),
    CHINESE("chino - china"),
    COLOMBIAN("colombiano - colombiana"),
    CONGOLESE("congoleño - congoleña"),
    COSTA_RICAN("costarricense - costarricense"),
    CUBAN("cubano - cubana"),
    ECUADORIAN("ecuatoriano - ecuatoriana"),
    EGYPTIAN("egipcio - egipcia"),
    SPANISH("español - española"),
    AMERICAN("estadounidense - estadounidense"),
    ETHIOPIAN("etíope - etíope"),
    FILIPINO("filipino - filipina"),
    INDIAN("hindú - hindú"),
    INDONESIAN("indonesio - indonesia"),
    IRANIAN("iraní - iraní"),
    JAPANESE("japonés - japonesa"),
    MEXICAN("mexicano - mexicana"),
    NIGERIAN("nigeriano - nigeriana"),
    PAKISTANI("paquistaní - paquistaní"),
    PARAGUAYAN("paraguayo - paraguaya"),
    PERUVIAN("peruano - peruana"),
    RUSSIAN("ruso - rusa"),
    THAI("tailandés - tailandesa"),
    TURKISH("turco - turca"),
    URUGUAYAN("uruguayo - uruguaya"),
    VENEZUELAN("venezolano - venezolana"),
    VIETNAMESE("vietnamita - vietnamita");

    private final String text;

    Nationality(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
