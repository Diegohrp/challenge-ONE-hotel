package com.example.hotel.controllers;

import com.example.hotel.DAO.GuestDAO;
import com.example.hotel.DAO.ReservationDAO;
import com.example.hotel.models.Guest;
import com.example.hotel.models.ReservationNumber;
import com.example.hotel.utils.GUIFeatures;
import com.example.hotel.utils.factory.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
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

    private Alert errorAlert, warningAlert, successAlert;

    private final String[] nationalities = {
        "alemán - alemana",
        "argentino - argentina",
        "bangladesí - bangladesí",
        "boliviano - boliviana",
        "brasileño - brasileña",
        "chileno - chilena",
        "chino - china",
        "colombiano - colombiana",
        "congoleño - congoleña",
        "costarricense - costarricense",
        "cubano - cubana",
        "ecuatoriano - ecuatoriana",
        "egipcio - egipcia",
        "español - española",
        "estadounidense - estadounidense",
        "etíope - etíope",
        "filipino - filipina",
        "hindú - hindú",
        "indonesio - indonesia",
        "iraní - iraní",
        "japonés - japonesa",
        "mexicano - mexicana",
        "nigeriano - nigeriana",
        "paquistaní - paquistaní",
        "paraguayo - paraguaya",
        "peruano - peruana",
        "ruso - rusa",
        "tailandés - tailandesa",
        "turco - turca",
        "uruguayo - uruguaya",
        "venezolano - venezolana",
        "vietnamita - vietnamita"
    };

    public RegisterController(){
        this.guest = new Guest();
        ConnectionFactory factory = new ConnectionFactory();
        this.reservationDAO = new ReservationDAO(factory.getConnection());
        this.guestDAO = new GuestDAO(factory.getConnection());
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        this.nationalityInput.getItems().addAll(nationalities);
        //first option, option by default
        this.nationalityInput.setValue(nationalities[0]);
        guest.setNationality(nationalities[0]);

        this.reservationNumber.setText(Long.toString(ReservationNumber.num));
        this.guest.setReservationId(ReservationNumber.num);

        this.createAlerts();
    }

    public void save(ActionEvent event) throws IOException{
        if (this.isDataValid()) {
            long guestId = guestDAO.register(this.guest);
            if(guestId > 0 ){
                successAlert.show();
                GUIFeatures.nextView("home.fxml","home.css",event);
            }else{
                //shows an error alert if an SQL exception happens
                errorAlert.setHeaderText("Algo salió mal");
                errorAlert.setContentText("Hubo un error al guardar la información.");
                errorAlert.show();
            }
        }else{
            this.errorAlert.show();
        }
    }

    public void goBack(ActionEvent event) throws IOException{
        this.warningAlert.setTitle("Regresar");
        this.warningAlert.setHeaderText("¿Estás seguro de que quieres regresar?");
        if(this.warningAlert.showAndWait().get() == ButtonType.OK){
            reservationDAO.delete(ReservationNumber.num);
            ReservationNumber.num = 0;
            GUIFeatures.nextView("reservation.fxml", "reservation.css", event);
        }
    }

    public void cancel(ActionEvent event) throws IOException{
        this.warningAlert.setTitle("Cancelar");
        this.warningAlert.setHeaderText("¿Estás seguro de que quieres cancelar?");
        if(this.warningAlert.showAndWait().get() == ButtonType.OK){
            reservationDAO.delete(ReservationNumber.num);
            ReservationNumber.num = 0;
            GUIFeatures.nextView("home.fxml", "home.css", event);
        }
    }

    /*
        Sets the guest's attributes with the values from the form
        The data is assigned to the guest object only when it's valid
    */
    public boolean isDataValid(){
        boolean validData = false;

        this.guest.setName(this.nameInput.getText());
        this.guest.setLastName(this.lastNameInput.getText());
        this.guest.setBirthdate(this.birthdateInput.getValue());
        this.guest.setNationality(this.nationalityInput.getValue());
        this.guest.setPhone(this.phoneInput.getText());

        if (this.emptyFields()) {
            this.errorAlert.setContentText(
                "Por favor completa todos los campos de manera correcta");
        } else if (this.guest.getName() == null) {
            this.errorAlert.setContentText(
                "Nombre debe tener al menos 3 " + "dígitos y no ser muy largo");
        } else if (this.guest.getLastName() == null) {
            this.errorAlert.setContentText("Apellido debe tener al menos 3 " +
                "dígitos y no ser muy largo");
        } else if (this.guest.getBirthdate() == null) {
            this.errorAlert.setContentText("El huésped no es mayor de edad");
        } else if (this.guest.getPhone() == null) {
            this.errorAlert.setContentText("Formato de teléfono no válido");
        } else {
            validData = true;
        }
        return validData;
    }
    private boolean emptyFields(){
        return this.guest.getName() == null && this.guest.getLastName() == null &&
            this.guest.getBirthdate() == null && this.guest.getPhone() == null;
    }

    private void createAlerts(){
        errorAlert = GUIFeatures.createAlert(
            "Error al registrar al huésped",
            "Datos incorrectos",
            "Por favor completa todos los campos de manera correcta",
            Alert.AlertType.ERROR
        );
        warningAlert = GUIFeatures.createAlert(
            "Regresar",
            "¿Estás seguro de que quieres regresar?",
            "Se perderá la información actual y se eliminará la " +
                "reservación.",
            Alert.AlertType.WARNING
        );
        warningAlert.getButtonTypes().add(ButtonType.CANCEL);
        successAlert = GUIFeatures.createAlert(
            "Registro exitoso",
            "Se ha realizado el registro de manera exitosa.",
            "Proceso de reservación completado",
            Alert.AlertType.INFORMATION);
    }
}
