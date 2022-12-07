package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.dao.UserDao;
import com.podzirei.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
