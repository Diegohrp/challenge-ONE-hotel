package com.example.hotel.controllers;

import com.example.hotel.DAO.ReservationDAO;

import com.example.hotel.enums.PaymentType;
import com.example.hotel.models.Reservation;
import com.example.hotel.models.ReservationNumber;
import com.example.hotel.utils.GUIFeatures;
import com.example.hotel.utils.factory.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;


public class ReservationController implements Initializable {
    @FXML
    private DatePicker checkIn, checkOut;
    @FXML
    private Label totalPrice, errorCheckIn, errorCheckOut;
    @FXML
    private ComboBox<PaymentType> paymentMethod;
    private final Reservation reservation;
    private final ReservationDAO reservationDAO;
    private Alert errorAlert, warningAlert, successAlert;
    private boolean isDataValid;

    public ReservationController(){
        this.reservation = new Reservation();
        ConnectionFactory connection = new ConnectionFactory();
        this.reservationDAO = new ReservationDAO(connection.getConnection());
        this.isDataValid = false;
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        this.initComboBox();
        this.initDatePickers();
        this.createAlerts();

    }

    public void next(ActionEvent event) throws IOException{
        //Stores the reservation in the DB if the data is valid
        //Sets the id from the DB to the static variable ReservationNumber.num
        this.validateData();
        if (this.isDataValid) {
            long reservationId = reservationDAO.add(reservation);
            if (reservationId > 0) {
                ReservationNumber.num = reservationId;
                successAlert.setContentText(
                    "El código de la reservación es: " + ReservationNumber.num);
                successAlert.show();
                GUIFeatures.nextView("register.fxml", "register.css", event);
            } else {
                //shows an error alert if an SQL exception happens
                errorAlert.setHeaderText("Algo salió mal");
                errorAlert.setContentText("Hubo un error al guardar la información.");
                errorAlert.show();
            }
        } else {
            //Shows an error alert if the data is not valid
            errorAlert.show();
        }
    }

    public void back(ActionEvent event) throws IOException{
        if (warningAlert.showAndWait().get() == ButtonType.OK) {
            GUIFeatures.nextView("home.fxml", "home.css", event);
        }
    }

    /*
        The order of date assignments depends on the interaction with the respective
        DatePicker.
        Both setters from the reservation instance must be executed to make the error
        messages disappear and set the isDatValid variable to true.
    */
    public void setCheckIn(ActionEvent event){
        boolean isCheckInValid = this.reservation.setCheckIn(this.checkIn.getValue());
        boolean isCheckOutValid = this.reservation.setCheckOut(this.checkOut.getValue());
        this.validateDates(isCheckInValid, isCheckOutValid);

    }
    public void setCheckOut(ActionEvent event){
        boolean isCheckOutValid = this.reservation.setCheckOut(this.checkOut.getValue());
        boolean isCheckInValid = this.reservation.setCheckIn(this.checkIn.getValue());
        this.validateDates(isCheckInValid, isCheckOutValid);
    }


    public void validateDates(boolean validCheckIn, boolean validCheckOut){

        //Shows an error message in a label if the date is assigned and is not valid
        if(this.reservation.getCheckIn() != null){
            GUIFeatures.ShowErrorLabel(validCheckIn, this.errorCheckIn,
                "Check-in no debe ser anterior a hoy ni posterior al check-out.");
        }
        if(this.reservation.getCheckOut() != null){
            GUIFeatures.ShowErrorLabel(validCheckOut, this.errorCheckOut,
                "Check-out debe ser posterior al check-in y a hoy.");
        }

        // reservation instance calculates the total when both dates are assigned
        this.reservation.calcTotal();
        this.totalPrice.setText(this.formatPrice(this.reservation.getTotal()));

        //for the general validation
        this.isDataValid = validCheckIn && validCheckOut;
    }

    //Assigns the payment method chosen in the ComboBox to the reservation object
    public void setPayment(ActionEvent event){
        this.reservation.setPayment(this.paymentMethod.getValue().getText());
    }

    private String formatPrice(Double total){
        Locale locale = new Locale("es", "MX");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return "$ " + numberFormat.format(total) + " MNX";
    }

    /*
        isDataValid comes with a true or false from executing setCheckIn or
        setCheckOut methods or false if those methods weren't even called
    */
    private void validateData(){
        this.isDataValid = this.isDataValid && this.reservation.getTotal() != 0.0 &&
            this.paymentMethod != null;
    }

    private void initComboBox(){
        ArrayList<PaymentType> paymentItems = new ArrayList<>();
        //Creates an array with all the elements from the PaymentType enum
        Collections.addAll(paymentItems, PaymentType.values());
        //Adds the array elements to the ComboBox
        paymentMethod.getItems().addAll(paymentItems);
        //Allows a ComboBox with text and an image
        paymentMethod.setCellFactory(param -> GUIFeatures.createListCell());
        paymentMethod.setButtonCell(GUIFeatures.createListCell());
        //When an option from the ComboBox is chosen, executes setPayment
        paymentMethod.setOnAction(this::setPayment);
        //payment method is cash by default
        this.paymentMethod.setValue(paymentItems.get(0));
    }

    private void initDatePickers(){
        //Defines a date format day/month/year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDateStringConverter converter = new LocalDateStringConverter(formatter,
            null);
        //Adds the format date to the Date pickers
        checkIn.setConverter(converter);
        checkOut.setConverter(converter);
        //The methods that must be executed when a date is chosen.
        checkIn.setOnAction(this::setCheckIn);
        checkOut.setOnAction(this::setCheckOut);
    }

    private void createAlerts(){
        errorAlert = GUIFeatures.createAlert("Error al reservar", "Datos incorrectos",
            "Por favor completa todos los campos de manera correcta",
            Alert.AlertType.ERROR);
        warningAlert = GUIFeatures.createAlert("Regresar",
            "¿Estás seguro de que quieres regresar?",
            "Si regresas, se perderá la información que no hayas guardado.",
            Alert.AlertType.WARNING);
        warningAlert.getButtonTypes().add(ButtonType.CANCEL);
        successAlert = GUIFeatures.createAlert("Reservación exitosa",
            "Se ha realizado la reservación de manera exitosa", "",
            Alert.AlertType.INFORMATION);
    }
}
