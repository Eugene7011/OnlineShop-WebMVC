package com.podzirei.onlineshop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping
    public String loginGet() {
        return "login";
    }

}
