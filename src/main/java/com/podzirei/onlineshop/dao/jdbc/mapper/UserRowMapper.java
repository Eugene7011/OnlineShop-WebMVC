package com.podzirei.onlineshop.dao.jdbc.mapper;

import com.podzirei.onlineshop.dao.jdbc.jdbcTemplate.RowMapper;
import com.podzirei.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            return User.builder()
                    .id(id)
                    .login(login)
                    .password(password)
                    .salt(salt)
                    .build();
        } catch (SQLException e){
            throw new RuntimeException("Can't extract user from database");
        }
    }
}
