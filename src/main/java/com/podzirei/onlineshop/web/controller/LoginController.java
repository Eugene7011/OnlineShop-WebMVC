package com.podzirei.onlineshop.web.controller;

import com.podzirei.onlineshop.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @GetMapping
    public String loginGet() {
        return "login";
    }

    @PostMapping
    public String loginPost(@RequestParam(value = "login") String login,
                            @RequestParam(value = "password") String password,
                            HttpServletResponse response) throws IOException {

        Cookie cookie = securityService.login(login, password);

        if (cookie != null) {
            response.addCookie(cookie);
            return "redirect:/products";
        } else {
            response.getWriter().write("<h3 style=position:absolute;left:33%;>Please enter correct login and password! </h3>");
            return "login";
        }
    }
}
