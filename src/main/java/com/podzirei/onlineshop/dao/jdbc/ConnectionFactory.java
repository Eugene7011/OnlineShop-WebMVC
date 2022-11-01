package com.podzirei.onlineshop.dao.jdbc;

import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Setter
public class ConnectionFactory {

    private String url;
    private String user;
    private String password;

    public Connection connectionToDatabase() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Cant connect to database. Check your root, password or user`s name in database");
        }
        return connection;
    }
}

