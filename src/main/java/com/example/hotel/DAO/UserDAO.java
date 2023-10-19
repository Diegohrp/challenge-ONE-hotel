package com.example.hotel.DAO;

import com.example.hotel.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection){
        this.connection = connection;
    }

    public String getPasswordHash(User user){

        try {
            final PreparedStatement statement = connection.prepareStatement(
                "SELECT password FROM users WHERE username = ?"
            );
            statement.setString(1, user.getUserName());
            statement.execute();

            try (statement) {
                final ResultSet resultSet = statement.getResultSet();
                try(resultSet){
                    if(resultSet.next()){
                        return resultSet.getString(1);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
