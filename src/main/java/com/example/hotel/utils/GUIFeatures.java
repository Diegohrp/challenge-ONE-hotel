package com.example.hotel.utils;

import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUIFeatures {

    public static void nextView(String view, String css,
                                ActionEvent event) throws IOException{

        FXMLLoader nextRoot = new FXMLLoader(Main.class.getResource("views/" + view));
        //gets the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(nextRoot.load());

        String styles = Objects.requireNonNull(
            Main.class.getResource("styles/" + css)).toExternalForm();
        scene.getStylesheets().add(styles);

        //adds the new scene to the current stage
        stage.setScene(scene);
        stage.show();
    }

    public static void ShowErrorLabel(boolean isValid, Label errorLabel, String errorMsg){
        if (isValid) {
            errorLabel.setText("");
        } else {
            errorLabel.setText(errorMsg);
        }
    }

    public static Alert createAlert(
        String title,
        String header,
        String content,
        Alert.AlertType type){

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static void exit(Stage stage){
        Alert alert = createAlert(
            "Salir",
            "¿Estás seguro de que deseas salir de la aplicación?",
            "Si sales de la aplicación, perderás todos los cambios no guardados. " +
                "¿Deseas continuar?",
            Alert.AlertType.CONFIRMATION
        );

        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
}
