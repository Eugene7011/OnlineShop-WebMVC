package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.dao.UserDao;
import com.podzirei.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Optional<User> findUser(String login) {
        return userDao.findUser(login);
    }
}
