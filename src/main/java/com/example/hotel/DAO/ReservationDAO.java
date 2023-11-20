package com.example.hotel.DAO;

import com.example.hotel.models.Reservation;

import java.sql.*;
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

    public int delete(long id){
        try {
            final PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM reservations WHERE id=?");
            statement.setLong(1, id);
            statement.execute();
            try (statement) {
                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
