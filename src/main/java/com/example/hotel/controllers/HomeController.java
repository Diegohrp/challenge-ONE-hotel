package com.example.hotel.controllers;

import com.example.hotel.utils.GUIFeatures;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeController {

    public void goToReservation(ActionEvent event) throws IOException{
        GUIFeatures.nextView("reservation.fxml","reservation.css",event);
    }

    public void goToSearch(ActionEvent event) throws IOException{
        GUIFeatures.nextView("search.fxml","search.css",event);
    }

    public void logout(ActionEvent event) throws IOException{
        GUIFeatures.nextView("login.fxml","login.css",event);
    }
}
