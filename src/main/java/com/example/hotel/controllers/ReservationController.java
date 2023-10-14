package com.example.hotel.controllers;

import com.example.hotel.utils.GUIFeatures;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ReservationController {

    public void next(ActionEvent event) throws IOException{
        GUIFeatures.nextView("register.fxml","register.css",event);
    }
}
