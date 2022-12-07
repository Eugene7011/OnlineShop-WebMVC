package com.podzirei.onlineshop.dao.repository;

import com.google.common.collect.Lists;
import com.podzirei.onlineshop.dao.UserDao;
import com.podzirei.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.podzirei.onlineshop.security.UserRole.ADMIN;
import static com.podzirei.onlineshop.security.UserRole.USER;

@Repository
@RequiredArgsConstructor
public class MockUserDao implements UserDao {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findUser(String login) {
        return getUser()
                .stream()
                .filter(applicationUser -> login.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<User> getUser() {
        return Lists.newArrayList(
                new User(
                        1,
                        "user",
                        passwordEncoder.encode("pass"),
                        USER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new User(2,
                        "admin",
                        passwordEncoder.encode("admin"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }


}
