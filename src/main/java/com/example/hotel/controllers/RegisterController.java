package com.example.hotel.controllers;

import com.example.hotel.models.ReservationNumber;
import com.example.hotel.utils.GUIFeatures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField reservationNumber;

    public void save(ActionEvent event) throws IOException{
        GUIFeatures.nextView("home.fxml","home.css",event);
    }

    public void goBack(ActionEvent event) throws IOException{
        GUIFeatures.nextView("reservation.fxml","reservation.css",event);
    }


    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        this.reservationNumber.setText(Long.toString(ReservationNumber.num));
    }
}
