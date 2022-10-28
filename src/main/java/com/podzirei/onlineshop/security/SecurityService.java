package com.podzirei.onlineshop.security;

import com.podzirei.onlineshop.entity.User;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import com.podzirei.onlineshop.service.UserService;

import java.util.Objects;

@NoArgsConstructor
@Setter
public class SecurityService {
    private UserService userService;

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

}


