package com.example.hotel.DAO;

import com.example.hotel.models.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDAO {
    private final Connection connection;

    public ReservationDAO(Connection connection){
        this.connection = connection;
    }

    public long add(Reservation reservation){
        try {
            final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reservations (check_in,check_out,total,payment) " +
                    "VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(reservation.getCheckIn()));
            statement.setDate(2, Date.valueOf(reservation.getCheckOut()));
            statement.setDouble(3, reservation.getTotal());
            statement.setString(4, reservation.getPayment());
            statement.execute();
            try (statement) {
                final ResultSet resultSet = statement.getGeneratedKeys();
                try (resultSet) {
                    if (resultSet.next()) {
                        return resultSet.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /*
        This method is used when the user is in register view and wants to return to
        reservation view. The previous registered reservations must be deleted.
    */
    public void delete(long id){
        try {
            final PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM reservations WHERE id=?");
            try (statement) {
                statement.setLong(1, id);
                statement.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /*
        This method is used when the user is in Search view at Reservation Tab and
        wants to delete the reservation in the DB.
    */

    public int fullDelete(long id) throws SQLException{
        try {
            this.connection.setAutoCommit(false);

            final PreparedStatement guestStatement = this.connection.prepareStatement(
                "DELETE FROM guests WHERE reservation_id=?");
            final PreparedStatement reservationStatement = this.connection.prepareStatement(
                "DELETE FROM reservations WHERE id=?");

            try (guestStatement; reservationStatement) {
                int count = 0;

                guestStatement.setLong(1, id);
                guestStatement.execute();
                count += guestStatement.getUpdateCount();

                reservationStatement.setLong(1, id);
                reservationStatement.execute();
                count += guestStatement.getUpdateCount();

                this.connection.commit();

                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.connection.rollback();
            return 0;
        } finally {
            this.connection.setAutoCommit(true);
        }
    }

    public ArrayList<Reservation> getAll(){
        final ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            final Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM reservations");
            try (statement) {
                ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                        Reservation reservation = new Reservation(resultSet.getLong("id"),
                            resultSet.getDate("check_in").toLocalDate(),
                            resultSet.getDate("check_out").toLocalDate(),
                            resultSet.getDouble("total"), resultSet.getString("payment"));
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public int edit(Reservation reservation){
        try {
            final PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE reservations SET check_in=?,check_out=?,total=?,payment=? " +
                    "WHERE id=?");
            try (statement) {

                statement.setDate(1, Date.valueOf(reservation.getCheckIn()));
                statement.setDate(2, Date.valueOf(reservation.getCheckOut()));
                statement.setDouble(3, reservation.getTotal());
                statement.setString(4, reservation.getPayment());
                statement.setLong(5, reservation.getId());
                statement.execute();

                return statement.getUpdateCount();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Reservation> getAllById(long id){
        ArrayList<Reservation> result = new ArrayList<>();
        try {
            final PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM reservations WHERE id = ? OR id LIKE ?");
            try (statement) {
                statement.setLong(1, id);
                statement.setString(2, "%" + id + "%");
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                        Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getDate("check_in").toLocalDate(),
                            resultSet.getDate("check_out").toLocalDate(),
                            resultSet.getDouble("total"),
                            resultSet.getString("payment"));
                        result.add(reservation);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
