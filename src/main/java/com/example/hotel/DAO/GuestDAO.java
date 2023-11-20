package com.example.hotel.DAO;

import com.example.hotel.models.Guest;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public int delete(long id){
        try {
            final PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM guests WHERE id=?");
            try (statement) {
                statement.setLong(1, id);
                statement.execute();
                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                statement.setLong(6,guest.getId());
                statement.execute();
                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
