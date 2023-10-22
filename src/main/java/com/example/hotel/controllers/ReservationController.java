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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;


public class ReservationController implements Initializable {
    @FXML
    private DatePicker checkIn,checkOut;
    @FXML
    private Label totalPrice, errorCheckIn,errorCheckOut;
    @FXML
    private ComboBox<PaymentType> paymentMethod;
    private final Reservation reservation;
    private final ReservationDAO reservationDAO;
    private Alert errorAlert,warningAlert,successAlert;

    public ReservationController(){
        this.reservation = new Reservation();
        ConnectionFactory connection = new ConnectionFactory();
        this.reservationDAO = new ReservationDAO(connection.getConnection());
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        this.initComboBox();
        this.initDatePickers();
        this.createAlerts();

    }

    public void next(ActionEvent event) throws IOException{
        //Stores the reservation in the DB if the data is valid
        //Sets the id from the DB to the static variable ReservationNumber.num
        if(this.isDataValid()){
            long reservationId = reservationDAO.addReservation(reservation);
            if(reservationId > 0){
                ReservationNumber.num = reservationId;
                successAlert.setContentText(
                    "El código de la reservación es: " +ReservationNumber.num);
                successAlert.show();
                GUIFeatures.nextView("register.fxml","register.css",event);
            }else{
                //shows an error alert if an SQL exception happens
                errorAlert.setHeaderText("Algo salió mal");
                errorAlert.setContentText("Hubo un error al guardar la información.");
                errorAlert.show();
            }
        }else{
            //Shows an error alert if the data is not valid
          errorAlert.show();
        }
    }

    public void back(ActionEvent event) throws IOException{
        if(warningAlert.showAndWait().get() == ButtonType.OK){
            GUIFeatures.nextView("home.fxml","home.css",event);
        }
    }

    //Shows an error message in a label if the date is not valid
    //reservation instance calculates the total when both dates are assigned
    //Shows the total value in the label "totalPrice"
    private void verifyDate(boolean isValid, Label errorLabel, String errorMsg){
        GUIFeatures.ShowErrorLabel(isValid,errorLabel,errorMsg);
        this.totalPrice.setText(this.formatPrice(this.reservation.getTotal()));
    }

    //if checkIn value is correct, reservation object returns true
    //if checkIn value is incorrect, reservation.setCheckIn doesn't assign the value
    public void setCheckIn(ActionEvent event){
        boolean isValid = this.reservation.setCheckIn(this.checkIn.getValue());
        this.verifyDate(
            isValid,
            this.errorCheckIn,
            "Check-in no debe ser anterior a hoy ni posterior al check-out.");

    }
    //if checkOut value is correct, reservation object returns true
    //if checkOut value is incorrect, reservation.setCheckOut doesn't assign the value
    public void setCheckOut(ActionEvent event){
        boolean isValid = this.reservation.setCheckOut(this.checkOut.getValue());
        this.verifyDate(
            isValid,
            this.errorCheckOut,
            "Check-out debe ser posterior al check-in y a hoy.");
    }

    //Assigns the payment method chosen in the ComboBox to the reservation object
    public void setPayment(ActionEvent event){
        this.reservation.setPayment(this.paymentMethod.getValue().getText());
    }

    private String formatPrice(Double total){
        Locale locale = new Locale("es","MX");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return "$ "+numberFormat.format(total)+" MNX";
    }

    private boolean isDataValid(){
        //When all the values are assigned, that means they're valid
        return this.reservation.getCheckIn() != null &&
        this.reservation.getCheckOut() != null &&
        this.reservation.getTotal() != null &&
        this.paymentMethod != null;
    }

    private void initComboBox(){
        ArrayList<PaymentType> paymentItems = new ArrayList<>();
        //Creates an array with all the elements from the PaymentType enum
        Collections.addAll(paymentItems, PaymentType.values());
        //Adds the array elements to the ComboBox
        paymentMethod.getItems().addAll(paymentItems);
        //Allows a ComboBox with text and an image
        paymentMethod.setCellFactory(param->GUIFeatures.createListCell());
        paymentMethod.setButtonCell(GUIFeatures.createListCell());
        //When an option from the ComboBox is chosen, executes setPayment
        paymentMethod.setOnAction(this::setPayment);
        //payment method is cash by default
        this.paymentMethod.setValue(paymentItems.get(0));
    }

    private void initDatePickers(){
        //Defines a date format day/month/year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDateStringConverter converter= new LocalDateStringConverter(formatter,null);
        //Adds the format date to the Date pickers
        checkIn.setConverter(converter);
        checkOut.setConverter(converter);
        //The methods that must be executed when a date is chosen.
        checkIn.setOnAction(this::setCheckIn);
        checkOut.setOnAction(this::setCheckOut);
    }

    private void createAlerts(){
        errorAlert = GUIFeatures.createAlert(
            "Error al reservar",
            "Datos incorrectos",
            "Por favor completa todos los campos de manera correcta",
            Alert.AlertType.ERROR
        );
        warningAlert = GUIFeatures.createAlert(
            "Regresar",
            "¿Estás seguro de que quieres regresar?",
            "Si regresas, se perderá la información que no hayas guardado.",
            Alert.AlertType.WARNING
        );
        warningAlert.getButtonTypes().add(ButtonType.CANCEL);
        successAlert = GUIFeatures.createAlert(
            "Reservación exitosa",
            "Se ha realizado la reservación de manera exitosa",
            "",
            Alert.AlertType.INFORMATION);
    }
}
