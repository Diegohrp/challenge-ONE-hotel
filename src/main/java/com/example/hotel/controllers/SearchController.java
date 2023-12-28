package com.example.hotel.controllers;

import com.example.hotel.DAO.GuestDAO;
import com.example.hotel.DAO.ReservationDAO;
import com.example.hotel.enums.Nationality;
import com.example.hotel.enums.PaymentType;
import com.example.hotel.models.Guest;
import com.example.hotel.models.Reservation;
import com.example.hotel.utils.Alerts;
import com.example.hotel.utils.GUIFeatures;
import com.example.hotel.utils.factory.ConnectionFactory;
import com.example.hotel.utils.validate.Validate;
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
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private TextField searchBar;
    private final ReservationDAO reservationDAO;
    private final GuestDAO guestDAO;

    private ObservableList<Reservation> reservationsList;
    private ObservableList<Guest> guestsList;
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
        this.guestsList = FXCollections.observableList(this.guestDAO.getAll());
        this.guestsTable.setItems(this.guestsList);
    }

    public void showReservations(){
        this.reservationsList = FXCollections.observableList(
            this.reservationDAO.getAll());
        this.reservationsTable.setItems(this.reservationsList);
    }

    @Override public void initialize(URL url, ResourceBundle resourceBundle){
        this.initGuestsSection();
        this.initReservationsSection();
    }

    public void edit(ActionEvent event){
        String tabName = tabPane.getSelectionModel().getSelectedItem().getText();
        if (tabName.equals("Huéspedes")) {
            this.editGuestInfo();
        } else {
            this.editReservation();
        }
    }

    public void delete(ActionEvent event){
        String tabName = tabPane.getSelectionModel().getSelectedItem().getText();
        try {
            if (tabName.equals("Huéspedes")) {
                this.deleteGuest();
            } else {
                this.deleteReservation();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void editGuestInfo(){
        Guest guest = this.getSelectedGuest();
        if (guest != null) {
            Alert errorAlert = Alerts.wrongRegisterDataAlert();

            if (GUIFeatures.isGuestDataValid(guest, errorAlert)) {
                if (this.guestDAO.edit(guest) > 0) {
                    Alerts.successUpdateAlert();
                } else {
                    Alerts.internalErrorAlert();
                }
            } else {
                errorAlert.show();
            }
        } else {
            Alerts.showNoSelectedFieldAlert();
        }
    }

    private void editReservation(){
        if (!this.validDates) {
            Alerts.wrongDatesAlert();
        } else {
            Reservation reservation = this.getSelectedReservation();
            if (reservation != null) {
                if (reservationDAO.edit(reservation) > 0) {
                    Alerts.successUpdateAlert();
                } else {
                    Alerts.internalErrorAlert();
                }
            } else {
                Alerts.showNoSelectedFieldAlert();
            }
        }
    }

    private void deleteGuest() throws SQLException{
        Guest guest = this.getSelectedGuest();
        if (guest != null) {
            if (guestDAO.delete(guest) > 1) {
                Alerts.successDeleteAlert();
                this.showGuests();
                this.showReservations();
            }
        } else {
            Alerts.showNoSelectedFieldAlert();
        }
    }

    private void deleteReservation() throws SQLException{
        Reservation reservation = this.getSelectedReservation();
        if (reservation != null) {
            if (this.reservationDAO.fullDelete(reservation.getId()) > 1) {
                Alerts.successDeleteAlert();
                this.showGuests();
                this.showReservations();
            } else {
                Alerts.showNoSelectedFieldAlert();
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

    public void search(ActionEvent event){
        if (!this.searchBar.getText().isBlank()) {
            String tabName = this.tabPane.getSelectionModel().getSelectedItem().getText();
            if (tabName.equals("Huéspedes")) {
                this.searchGuest(searchBar.getText());
            } else {
                if (Validate.isNumericLong(this.searchBar.getText())) {
                    this.searchReservation(Long.parseLong(this.searchBar.getText()));
                } else {
                    Alerts.wrongIdAlert();
                }
            }
        }
    }

    private void searchGuest(String lastName){
        this.guestsList = FXCollections.observableList(this.guestDAO.getAll(lastName));
        this.guestsTable.setItems(this.guestsList);
    }

    private void searchReservation(long id){
        this.reservationsList = FXCollections.observableList(
            this.reservationDAO.getAllById(id));
        this.reservationsTable.setItems(this.reservationsList);
    }

    public void goBack(ActionEvent event) throws IOException{
        GUIFeatures.nextView("home.fxml","home.css",event);
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
}