package com.podzirei.onlineshop.dao.jdbc;

import com.podzirei.onlineshop.dao.UserDao;
import com.podzirei.onlineshop.dao.jdbc.jdbcTemplate.JdbcTemplate;
import com.podzirei.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.podzirei.onlineshop.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {
    private static final String FIND_USER_SQL = "SELECT id, login, password, salt FROM users WHERE login=?";
    private static final UserRowMapper userRowMapper = new UserRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUser(String login) {
        Object[] args = {login};
        return jdbcTemplate.queryObject(FIND_USER_SQL, userRowMapper, args);
    }
}







