package com.podzirei.onlineshop.security;

import com.podzirei.onlineshop.entity.User;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import com.podzirei.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Setter
public class SecurityService {
    @Autowired
    private UserService userService;

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
        if (userFromDB != null) {
            String encryptedPassword = encryptPasswordWithSalt(password, login);
            String passwordFromDB = userFromDB.getPassword();
            return Objects.equals(encryptedPassword, passwordFromDB);
        }
        return false;
    }

    public String login(String login, String password) {
        if (isValidCredential(login, password)) {
            return userService.generateCookie().getValue();
        }
        return null;
    }

    public String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    public String getSalt(String login) {
        User userLogin = userService.findUser(login);
        return userLogin.getSalt();
    }

    //TODO: check usage if needed
    public String generateSalt() {
        return UUID.randomUUID().toString();
    }
}


