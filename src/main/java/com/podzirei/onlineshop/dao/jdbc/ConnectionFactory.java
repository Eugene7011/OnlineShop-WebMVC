package com.podzirei.onlineshop.dao.jdbc;

import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Setter
public class ConnectionFactory {

    private String url;
    private String driver;
    private String user;
    private String password;

    private final Properties properties = new Properties();

    public Connection connectionToDatabase() {
        try {
            Class.forName(properties.getProperty(JDBC_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC_DRIVER is not running");
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(properties.getProperty(DB_URL), properties.getProperty(USER), properties.getProperty(PASS));
        } catch (SQLException e) {
            throw new RuntimeException("Cant connect to database. Check your root, password or user`s name in database");
        }
        return connection;
    }
}

