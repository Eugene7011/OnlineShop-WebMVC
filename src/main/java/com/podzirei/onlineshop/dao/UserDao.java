package com.podzirei.onlineshop.dao;

import com.podzirei.onlineshop.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findUser(String login);
}
