package com.example.hotel.DAO;

import com.example.hotel.models.Guest;
import java.sql.*;
import java.util.ArrayList;


public class GuestDAO {
    private final Connection connection;

    public GuestDAO(Connection connection){
        this.connection = connection;
    }

    public long register(Guest guest){
        try {
            final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO guests (name,last_name,birthday,nationality,phone," +
                    "reservation_id) values (?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                statement.setString(1, guest.getName());
                statement.setString(2, guest.getLastName());
                statement.setDate(3, Date.valueOf(guest.getBirthdate()));
                statement.setString(4, guest.getNationality());
                statement.setString(5, guest.getPhone());
                statement.setLong(6, guest.getReservationId());
                statement.execute();
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

    public int delete(Guest guest) throws SQLException{
        try {

            this.connection.setAutoCommit(false);

            final PreparedStatement guestStatement = connection.prepareStatement(
                "DELETE FROM guests WHERE id=?");

            final PreparedStatement reservationStatement = connection.prepareStatement(
                "DELETE FROM reservations WHERE id=?");

            try (guestStatement; reservationStatement) {
                int count = 0;

                guestStatement.setLong(1, guest.getId());
                guestStatement.execute();
                count += guestStatement.getUpdateCount();

                reservationStatement.setLong(1, guest.getReservationId());
                reservationStatement.execute();
                count += reservationStatement.getUpdateCount();

                this.connection.commit();
                return count;
            }


        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
            return 0;

        } finally {
            this.connection.setAutoCommit(true);
        }

    }

    public ArrayList<Guest> getAll(){
        ArrayList<Guest> guests = new ArrayList<>();
        try {
            final Statement statement = connection.createStatement();

            try (statement) {
                statement.execute("SELECT * FROM guests");
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                        Guest guest = new Guest(resultSet.getLong("id"),
                            resultSet.getString("name"), resultSet.getString("last_name"),
                            resultSet.getDate("birthday").toLocalDate(),
                            resultSet.getString("nationality"),
                            resultSet.getString("phone"),
                            resultSet.getLong("reservation_id"));
                        guests.add(guest);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    public int edit(Guest guest){
        try {
            final PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE guests SET name=?,last_name=?,birthday=?,nationality=?,phone=? " +
                    "WHERE id=?");

            try (statement) {
                statement.setString(1, guest.getName());
                statement.setString(2, guest.getLastName());
                statement.setDate(3, Date.valueOf(guest.getBirthdate()));
                statement.setString(4, guest.getNationality());
                statement.setString(5, guest.getPhone());
                statement.setLong(6, guest.getId());
                statement.execute();
                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Guest> getAll(String lastName){
        ArrayList<Guest> result = new ArrayList<>();
        try {
            final PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM guests WHERE last_name LIKE ?");
            try (statement) {
                statement.setString(1, "%" + lastName + "%");
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                        Guest guest = new Guest(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("last_name"),
                            resultSet.getDate("birthday").toLocalDate(),
                            resultSet.getString("nationality"),
                            resultSet.getString("phone"),
                            resultSet.getLong("reservation_id"));
                        result.add(guest);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
