package com.example.hotel.controllers;


import com.example.hotel.utils.GUIFeatures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;

    public void login(ActionEvent event) throws IOException{
        GUIFeatures.nextView("home.fxml","home.css",event);
    }


}
