package com.example.hotel.DAO;

import com.example.hotel.models.Guest;

import java.sql.*;
import java.time.LocalDate;

public class GuestDAO {
    private Connection connection;

    public GuestDAO(Connection connection){
        this.connection = connection;
    }

    public long register(Guest guest){
        try {
            final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO guests (name,last_name,birthday,nationality,phone," +
                    "reservation_id) values (?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, guest.getName());
            statement.setString(2, guest.getLastName());
            statement.setDate(3, Date.valueOf(guest.getBirthdate()));
            statement.setString(4, guest.getNationality());
            statement.setString(5, guest.getPhone());
            statement.setLong(6, guest.getReservationId());
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
                "DELETE FROM guests WHERE id=?");
            statement.setLong(1, id);
            statement.execute();
            try (statement) {
                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
