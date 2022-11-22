package com.podzirei.onlineshop.dao.jdbc.jdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    public static ResultSet getConnectionAndExecute(String sql, DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public static int executeUpdate(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }

    public static PreparedStatement getConnectionAndPrepare(String sql, DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection.prepareStatement(sql);
    }

    public static ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }
}
