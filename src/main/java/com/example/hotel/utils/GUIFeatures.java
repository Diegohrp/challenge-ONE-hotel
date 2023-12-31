package com.example.hotel.utils;

import com.example.hotel.Main;
import com.example.hotel.enums.PaymentType;
import com.example.hotel.models.Guest;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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

    private static void setComboBoxItem(ListCell<PaymentType> list, PaymentType item,
                                        boolean empty){

        if (empty || item == null) {
            list.setGraphic(null);
        } else {
            HBox hBox = new HBox(5);
            Label label = new Label();
            label.setText(item.getText());

            Image image = new Image(Objects.requireNonNull(
                Main.class.getResourceAsStream("assets/icons/" + item.getIcon())));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);

            hBox.getChildren().addAll(label, imageView);
            hBox.setAlignment(Pos.CENTER);

            list.setGraphic(hBox);


        }
    }

    public static ListCell<PaymentType> createListCell(){
        return new ListCell<>() {
            @Override protected void updateItem(PaymentType paymentType, boolean empty){
                super.updateItem(paymentType, empty);
                GUIFeatures.setComboBoxItem(this, paymentType, empty);
            }
        };
    }

    public static boolean isGuestDataValid(Guest guest, Alert errorAlert){
        boolean validData = false;
        boolean emptyFields = guest.getName() == null && guest.getLastName() == null &&
            guest.getBirthdate() == null && guest.getNationality() == null &&
            guest.getPhone() == null;
        errorAlert.setHeaderText("Datos incorrectos");
        if (emptyFields) {
            errorAlert.setContentText(
                "Por favor completa todos los campos de manera correcta");
        } else if (guest.getName() == null) {
            errorAlert.setContentText(
                "Nombre debe tener al menos 3 " + "dígitos y no ser muy largo");
        } else if (guest.getLastName() == null) {
            errorAlert.setContentText(
                "Apellido debe tener al menos 3 " + "dígitos y no ser muy largo");
        } else if (guest.getBirthdate() == null) {
            errorAlert.setContentText("El huésped no es mayor de edad");
        } else if (guest.getPhone() == null) {
            errorAlert.setContentText("Formato de teléfono no válido");
        } else {
            validData = true;
        }
        return validData;
    }
}
