package com.example.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(
            Main.class.getResource("views/login.fxml"));
        String css = Main.class
            .getResource("styles/login.css").toExternalForm();

        Scene scene = new Scene(root.load());
        scene.getStylesheets().add(css);
        stage.setTitle("Hotel");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}