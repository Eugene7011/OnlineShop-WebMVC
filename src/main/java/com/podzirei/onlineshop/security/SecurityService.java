package com.podzirei.onlineshop.security;

import com.podzirei.onlineshop.entity.User;
import com.podzirei.onlineshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SecurityService {
    private final CopyOnWriteArrayList<Cookie> cookies = new CopyOnWriteArrayList<>();
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
        if (userFromDB != null) {
            String encryptedPassword = encryptPasswordWithSalt(password, login);
            String passwordFromDB = userFromDB.getPassword();
            return Objects.equals(encryptedPassword, passwordFromDB);
        }
        return false;
    }

    public Cookie login(String login, String password) {
        if (isValidCredential(login, password)) {
            return generateCookie();
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

    public Cookie generateCookie() {
        String cookieId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("cookieId", cookieId);
        cookie.setMaxAge(60 * 60);
        cookie.setDomain("localhost");
        cookie.setPath(":8080/products");
        cookies.add(cookie);

        return cookie;
    }

    public boolean isAuth(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return false;
        }

        return validateCookies(request);
    }

    private Boolean validateCookies(HttpServletRequest request) {
        boolean result = false;
        Optional<String> cookieFromDB = Arrays.stream(request.getCookies())
                .filter(cookie -> ("cookieId").equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
        if (cookieFromDB.isPresent()) {
            result = true;
        }
        return result;
    }
}


