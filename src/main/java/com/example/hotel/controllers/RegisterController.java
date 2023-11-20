package com.example.hotel.controllers;

import com.example.hotel.DAO.GuestDAO;
import com.example.hotel.DAO.ReservationDAO;
import com.example.hotel.enums.Nationality;
import com.example.hotel.models.Guest;
import com.example.hotel.models.ReservationNumber;
import com.example.hotel.utils.Alerts;
import com.example.hotel.utils.GUIFeatures;
import com.example.hotel.utils.factory.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField nameInput, lastNameInput, phoneInput, reservationNumber;
    @FXML
    private ComboBox<String> nationalityInput;
    @FXML
    private DatePicker birthdateInput;
    private final Guest guest;
    private final GuestDAO guestDAO;
    private final ReservationDAO reservationDAO;


    public RegisterController(){
        this.guest = new Guest();
        ConnectionFactory factory = new ConnectionFactory();
        this.reservationDAO = new ReservationDAO(factory.getConnection());
        this.guestDAO = new GuestDAO(factory.getConnection());
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<String> nationalities = new ArrayList<>();
        for (Nationality item : Nationality.values()) {
            nationalities.add(item.getText());
        }

        this.nationalityInput.getItems().addAll(nationalities);
        //first option, option by default
        this.nationalityInput.setValue(nationalities.get(0));
        guest.setNationality(nationalities.get(0));

        this.reservationNumber.setText(Long.toString(ReservationNumber.num));
        this.guest.setReservationId(ReservationNumber.num);
    }

    public void save(ActionEvent event) throws IOException{
        /*
            Sets the guest's attributes with the values from the form
            The data is assigned to the guest object only when it's valid
        */
        this.guest.setName(this.nameInput.getText());
        this.guest.setLastName(this.lastNameInput.getText());
        this.guest.setBirthdate(this.birthdateInput.getValue());
        this.guest.setNationality(this.nationalityInput.getValue());
        this.guest.setPhone(this.phoneInput.getText());

        Alert errorAlert = Alerts.wrongRegisterDataAlert();

        if (GUIFeatures.isGuestDataValid(this.guest, errorAlert)) {
            long guestId = guestDAO.register(this.guest);
            if (guestId > 0) {
                Alerts.successRegisterAlert();
                GUIFeatures.nextView("home.fxml", "home.css", event);
            } else {
                //shows an error alert if an SQL exception happens
                Alerts.internalErrorAlert();
            }
        } else {
            errorAlert.show();
        }
    }

    public void goBack(ActionEvent event) throws IOException{
        Alert warningAlert = Alerts.cancelRegisterAlert();

        if (warningAlert.showAndWait().get() == ButtonType.OK) {
            reservationDAO.delete(ReservationNumber.num);
            ReservationNumber.num = 0;
            GUIFeatures.nextView("reservation.fxml", "reservation.css", event);
        }
    }

    public void cancel(ActionEvent event) throws IOException{
        Alert warningAlert = Alerts.cancelRegisterAlert();
        warningAlert.setTitle("Cancelar");
        warningAlert.setHeaderText("¿Estás seguro de que quieres cancelar?");

        if (warningAlert.showAndWait().get() == ButtonType.OK) {
            reservationDAO.delete(ReservationNumber.num);
            ReservationNumber.num = 0;
            GUIFeatures.nextView("home.fxml", "home.css", event);
        }
    }
}
