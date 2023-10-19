package com.example.hotel.utils.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private final DataSource dataSource;

    public ConnectionFactory(){
        Dotenv dotenv = Dotenv.configure().load();
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbUrl = dotenv.get("DB_URL");


        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setUser(dbUser);
        pooledDataSource.setPassword(dbPassword);
        pooledDataSource.setJdbcUrl(dbUrl);
        pooledDataSource.setMaxPoolSize(9);
        this.dataSource = pooledDataSource;
    }

    public Connection getConnection(){
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
