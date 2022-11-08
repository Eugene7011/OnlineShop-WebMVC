package com.podzirei.onlineshop.dao.jdbc;

import com.podzirei.onlineshop.dao.UserDao;
import com.podzirei.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.podzirei.onlineshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcUserDao implements UserDao {
    @Autowired
    private DataSource dataSource;
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private static final String FIND_USER_SQL = "SELECT id, login, password, salt FROM users WHERE login=?";

    @Override
    public User findUser(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_SQL)) {

            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    return userRowMapper.mapRow(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Can not search user with : " + login, e);
        }
    }
}







