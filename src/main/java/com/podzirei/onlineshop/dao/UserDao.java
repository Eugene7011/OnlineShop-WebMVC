package com.podzirei.onlineshop.dao;

import com.podzirei.onlineshop.entity.User;

public interface UserDao {
    User findUser(String login);
}
