package com.example.hotel.controllers;

import com.example.hotel.utils.GUIFeatures;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class HomeController {

    public void goToReservation(ActionEvent event) throws IOException{
        GUIFeatures.nextView("reservation.fxml","reservation.css",event);
    }

    public void goToSearch(ActionEvent event) throws IOException{
        GUIFeatures.nextView("search.fxml","search.css",event);
    }

    public void logout(ActionEvent event) throws IOException{
        Alert alert = GUIFeatures.createAlert(
            "Logout",
            "¿Estás seguro de que deseas cerrar sesión?",
            "Si cierras sesión, deberás volver a ingresar las credenciales para acceder" +
                " al sistema.",
            Alert.AlertType.CONFIRMATION
        );
        if(alert.showAndWait().get() == ButtonType.OK) {
            GUIFeatures.nextView("login.fxml","login.css",event);
        }
    }
}
