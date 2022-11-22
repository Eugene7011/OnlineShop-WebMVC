package com.podzirei.onlineshop.web.controller;

import com.podzirei.onlineshop.security.SecurityService;
import com.podzirei.onlineshop.security.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final static int MAX_AGE = 60 * 60;
    private final static String DOMAIN = "localhost";
    private final static String PATH = "localhost";
    private final SecurityService securityService;

    @GetMapping
    public String loginGet() {
        return "login";
    }

    @PostMapping
    public String loginPost(@RequestParam(value = "login") String login,
                            @RequestParam(value = "password") String password,
                            HttpServletResponse response) throws IOException {

        SecurityService.Credentials credentials = securityService.setCredentials(login, password);

        Optional<Session> optionalSession = securityService.login(credentials);
        if (optionalSession.isEmpty()) {
            response.getWriter().write("<h3 style=position:absolute;left:33%;>Please enter correct login and password! </h3>");
            return "login";
        }

        Session session = optionalSession.get();
        Cookie cookie = new Cookie("userToken", session.getToken().toString());
        cookie.setMaxAge(MAX_AGE);
        cookie.setDomain(DOMAIN);
        cookie.setPath(PATH);

        response.addCookie(cookie);
        return "redirect:/products";
    }
}
