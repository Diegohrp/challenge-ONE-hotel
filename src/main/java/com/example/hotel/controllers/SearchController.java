package com.example.hotel.controllers;

import com.example.hotel.DAO.GuestDAO;
import com.example.hotel.DAO.ReservationDAO;
import com.example.hotel.enums.Nationality;
import com.example.hotel.enums.PaymentType;
import com.example.hotel.models.Guest;
import com.example.hotel.models.Reservation;
import com.example.hotel.utils.GUIFeatures;
import com.example.hotel.utils.factory.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SearchController implements Initializable {
    @FXML
    private TableView<Guest> guestsTable;
    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<String, Long> guestIdCol, reservationCol, reservationIdCol;
    @FXML
    private TableColumn<String, String> nameCol, lastNameCol, phoneCol, paymentCol, nationalityCol;
    @FXML
    private TableColumn<String, LocalDate> birthdateCol, checkInCol, checkOutCol;
    @FXML
    private TableColumn<String, Double> totalCol;
    @FXML
    private TabPane tabPane;
    @FXML
    Alert successAlert, warningAlert, errorAlert;

    private final ReservationDAO reservationDAO;
    private final GuestDAO guestDAO;

    private ObservableList<Reservation> reservationsList;
    private boolean validDates;

    public SearchController(){
        ConnectionFactory factory = new ConnectionFactory();
        guestDAO = new GuestDAO(factory.getConnection());
        reservationDAO = new ReservationDAO(factory.getConnection());
        this.validDates = true;
    }

    private void initGuestsSection(){
        this.guestsValueFactory();
        this.makeGuestsTableEditable();
        this.showGuests();
    }

    private void initReservationsSection(){
        this.reservationsValueFactory();
        this.makeReservationsTableEditable();
        this.showReservations();
    }

    public void showGuests(){
        guestsTable.getItems().addAll(this.guestDAO.getAll());
    }

    public void showReservations(){
        this.reservationsList = FXCollections.observableList(
            this.reservationDAO.getAll());
        reservationsTable.setItems(this.reservationsList);
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        this.initGuestsSection();
        this.initReservationsSection();
        this.createAlerts();

    }

    public void edit(ActionEvent event){
        String tabName = tabPane.getSelectionModel().getSelectedItem().getText();
        if (tabName.equals("Huéspedes")) {
            this.editGuestInfo();
        } else {
            this.editReservation();
        }
    }

    private void editGuestInfo(){
        Guest guest = this.getSelectedGuest();
        if (guest != null) {
            if (GUIFeatures.isGuestDataValid(guest, this.errorAlert)) {
                if (this.guestDAO.edit(guest) > 0) {
                    this.successAlert.show();
                } else {
                    this.errorAlert.setHeaderText(
                        "Algo sucedió mal, por favor intente más tarde");
                    this.errorAlert.setContentText("");
                    this.errorAlert.show();
                }
            } else {
                this.errorAlert.show();
            }
        } else {
            errorAlert.setHeaderText("No has seleccionado ningún registro");
            errorAlert.setContentText("");
            errorAlert.show();
        }
    }

    private void editReservation(){
        if (!this.validDates) {
            errorAlert.show();
        } else {
            Reservation reservation = this.getSelectedReservation();
            if (reservation != null) {
                if (reservationDAO.edit(reservation) > 0) {
                    this.successAlert.show();
                } else {
                    this.errorAlert.setHeaderText(
                        "Algo sucedió mal, por favor intente más tarde");
                    this.errorAlert.setContentText("");
                    this.errorAlert.show();
                }
            }
        }

    }

    private void validateDates(String type, LocalDate date){
        boolean isCheckInValid;
        boolean isCheckoutValid;
        LocalDate otherDate;

        if (type.equals("CheckIn")) {
            isCheckInValid = this.getSelectedReservation().setCheckIn(date);
            otherDate = this.getSelectedReservation().getCheckOut();
            isCheckoutValid = this.getSelectedReservation().setCheckOut(otherDate);
        } else {
            isCheckoutValid = this.getSelectedReservation().setCheckOut(date);
            otherDate = this.getSelectedReservation().getCheckIn();
            isCheckInValid = this.getSelectedReservation().setCheckIn(otherDate);
        }

        if (!isCheckInValid || !isCheckoutValid) {
            errorAlert.setTitle("Datos incorrectos");
            errorAlert.setContentText(
                "Check-in no debe ser posterior a Chek-out y ambos deben ser " +
                    "posteriores a hoy");
        }

        //Calculates the total after setting both dates
        this.getSelectedReservation().calcTotal();

        this.validDates = isCheckInValid && isCheckoutValid;
    }

    private Guest getSelectedGuest(){
        return this.guestsTable.getSelectionModel().getSelectedItem();
    }

    private Reservation getSelectedReservation(){
        return this.reservationsTable.getSelectionModel().getSelectedItem();
    }


    private void guestsValueFactory(){
        this.guestIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.birthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        this.nationalityCol.setCellValueFactory(
            new PropertyValueFactory<>("nationality"));
        this.phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.reservationCol.setCellValueFactory(
            new PropertyValueFactory<>("reservationId"));
    }

    private void makeGuestsTableEditable(){
        this.guestsTable.setEditable(true);

        this.nameCol.setEditable(true);
        this.nameCol.setCellFactory(
            TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        this.nameCol.setOnEditCommit(
            event -> this.getSelectedGuest().setName(event.getNewValue()));

        this.lastNameCol.setEditable(true);
        this.lastNameCol.setCellFactory(
            TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        this.lastNameCol.setOnEditCommit(
            event -> this.getSelectedGuest().setLastName(event.getNewValue()));

        this.birthdateCol.setEditable(true);
        this.birthdateCol.setCellFactory(
            TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        this.birthdateCol.setOnEditCommit(
            event -> this.getSelectedGuest().setBirthdate(event.getNewValue()));

        this.phoneCol.setEditable(true);
        this.phoneCol.setCellFactory(
            TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        this.phoneCol.setOnEditCommit(
            event -> this.getSelectedGuest().setPhone(event.getNewValue()));

        this.nationalityCol.setEditable(true);
        List<String> list = Arrays.stream(Nationality.values()).map(
            item -> item.getText()).collect(Collectors.toList());
        /*
            When the user wants to edit this field, shows a combo box with the
            nationality options
        */
        this.nationalityCol.setCellFactory(
            ComboBoxTableCell.forTableColumn(FXCollections.observableList(list)));
        this.nationalityCol.setOnEditCommit(
            event -> this.getSelectedGuest().setNationality(event.getNewValue()));
    }

    private void reservationsValueFactory(){
        this.reservationIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        this.checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        this.totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));
    }


    private void makeReservationsTableEditable(){
        this.reservationsTable.setEditable(true);

        this.checkInCol.setEditable(true);
        this.checkInCol.setCellFactory(
            TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        this.checkInCol.setOnEditCommit(
            event -> this.validateDates("CheckIn", event.getNewValue()));

        this.checkOutCol.setEditable(true);
        this.checkOutCol.setCellFactory(
            TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        this.checkOutCol.setOnEditCommit(
            event -> this.validateDates("CheckOut", event.getNewValue()));

        /*
            When the user wants to edit this field, shows a combo box with the
            payment options
        */
        this.paymentCol.setEditable(true);
        List<String> list = Arrays.stream(PaymentType.values()).map(
            item -> item.getText()).collect(Collectors.toList());
        this.paymentCol.setCellFactory(
            ComboBoxTableCell.forTableColumn(FXCollections.observableList(list)));
        this.paymentCol.setOnEditCommit(
            event -> this.getSelectedReservation().setPayment(event.getNewValue()));
    }

    private void createAlerts(){
        this.successAlert = GUIFeatures.createAlert("Operación exitosa",
            "Registro editado con éxito",
            "Se ha guardado la información que has modificado",
            Alert.AlertType.INFORMATION);
        this.errorAlert = GUIFeatures.createAlert("Error en la operación",
            "Datos incorrectos", "Por favor completa todos los campos de manera correcta",
            Alert.AlertType.ERROR);
        warningAlert = GUIFeatures.createAlert("Regresar",
            "¿Estás seguro de que quieres regresar?",
            "Si regresas, se perderá la información que no hayas guardado.",
            Alert.AlertType.WARNING);
    }
}
