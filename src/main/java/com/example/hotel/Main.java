package com.example.hotel;

import com.example.hotel.utils.Alerts;
import com.example.hotel.utils.GUIFeatures;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    @Override public void start(Stage stage) throws IOException{
        FXMLLoader root = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        String css = Main.class.getResource("styles/home.css").toExternalForm();

        Scene scene = new Scene(root.load());
        scene.getStylesheets().add(css);
        stage.setTitle("Hotel");
        stage.setScene(scene);
        stage.setResizable(false);

        //Shows an alert to confirm if the user really wants to leave the app
        stage.setOnCloseRequest((WindowEvent event) -> {
            event.consume();
            Alerts.exitAppAndAlert(stage);
        });
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}