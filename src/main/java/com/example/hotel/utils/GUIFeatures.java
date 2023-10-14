package com.example.hotel.utils;

import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUIFeatures {

    public static void nextView(String view, String css, ActionEvent event) throws IOException{

        FXMLLoader nextRoot = new FXMLLoader(Main.class.getResource("views/"+view));
        //gets the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(nextRoot.load());

        String styles = Objects.requireNonNull(
            Main.class.getResource("styles/" + css))
            .toExternalForm();
        scene.getStylesheets().add(styles);

        //adds the new scene to the current stage
        stage.setScene(scene);
        stage.show();
    }
}
