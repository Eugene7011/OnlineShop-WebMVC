package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.dao.UserDao;
import com.podzirei.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User findUser(String login) {
        return userDao.findUser(login)
                .orElseThrow(() -> new NoSuchElementException("Can't find user with login " + login));
    }
}
