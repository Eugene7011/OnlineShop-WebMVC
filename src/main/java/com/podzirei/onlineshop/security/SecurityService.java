package com.podzirei.onlineshop.security;

import com.podzirei.onlineshop.entity.User;
import com.podzirei.onlineshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SecurityService {
    private final CopyOnWriteArrayList<UUID> sessions = new CopyOnWriteArrayList<>();
    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public String encryptPasswordWithSalt(String password, String login) {
        String salt = getSalt(login);
        String passwordWithSalt = password + salt;

        return hash(passwordWithSalt);
    }

    public boolean isValidCredential(String login, String password) {
        User userFromDB = userService.findUser(login);
        String encryptedPassword = encryptPasswordWithSalt(password, login);
        String passwordFromDB = userFromDB.getPassword();
        return Objects.equals(encryptedPassword, passwordFromDB);
    }

    public UUID login(String login, String password) {
        if (isValidCredential(login, password)) {
            return generateToken();
        }
        return null;
    }

    private UUID generateToken() {
        UUID token = UUID.randomUUID();
        sessions.add(token);
        return token;
    }

    public String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    public String getSalt(String login) {
        User userLogin = userService.findUser(login);
        return userLogin.getSalt();
    }

    public boolean isAuth(List<UUID> tokens) {
        return isValidToken(tokens);
    }

    private boolean isValidToken(List<UUID> tokens) {
        return tokens.stream()
                .anyMatch(sessions::contains);
    }
}


