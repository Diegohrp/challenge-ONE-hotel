package com.example.hotel.controllers;


import com.example.hotel.DAO.UserDAO;
import com.example.hotel.models.User;
import com.example.hotel.utils.Alerts;
import com.example.hotel.utils.GUIFeatures;
import com.example.hotel.utils.factory.ConnectionFactory;
import com.example.hotel.utils.validate.Validate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private final UserDAO userDAO;

    public LoginController(){
        ConnectionFactory pool = new ConnectionFactory();
        this.userDAO = new UserDAO(pool.getConnection());
    }

    public void exit(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alerts.exitAppAndAlert(currentStage);
    }

    public void login(ActionEvent event) throws IOException{
        /* Fields must be filled and have at least n characters with a maximum of m
           characters
        */
        boolean isValid = Validate.validateNonEmpty(userField.getText(), 15, 3) &&
            Validate.validateNonEmpty(passwordField.getText(), 20, 8);

        String errorMsg = "Completa todos los campos correctamente";
        //if the data is incorrect, shows the error label in red.
        GUIFeatures.ShowErrorLabel(isValid, errorLabel, errorMsg);
        //Sends the data to the DB only when it's in the correct format,
        if (isValid) {
            User user = new User(userField.getText(), passwordField.getText());
            //Gets the password's hash that is stored in the DB
            String hashedPassword = userDAO.getPasswordHash(user);
            /* if the hash from the password typed matches the stored one in the DB, it
               sends the user to the "home view"
            */
            if (user.authenticate(hashedPassword)) {
                GUIFeatures.nextView("home.fxml", "home.css", event);
            } else {
                Alerts.loginErrorAlert(event);
            }
        }

    }


}
