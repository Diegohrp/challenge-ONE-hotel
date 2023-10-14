package com.example.hotel.controllers;

import com.example.hotel.utils.GUIFeatures;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RegisterController {

    public void save(ActionEvent event) throws IOException{
        GUIFeatures.nextView("home.fxml","home.css",event);
    }

    public void goBack(ActionEvent event) throws IOException{
        GUIFeatures.nextView("reservation.fxml","reservation.css",event);
    }


}
