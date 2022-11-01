//package com.podzirei.onlineshop.web.controllers;
//
//import com.podzirei.onlineshop.security.SecurityService;
//import com.podzirei.onlineshop.web.util.PageGenerator;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Controller
//@Getter
//public class LoginController {
//
//    @Autowired
//    private SecurityService securityService;
//
//    @RequestMapping(path = "/login", method = RequestMethod.GET)
//    @ResponseBody
//    protected void loginGet(HttpServletResponse response) throws IOException {
//        PageGenerator pageGenerator = PageGenerator.getInstance();
//        String page = pageGenerator.getPage("login.html");
//        response.getWriter().write(page);
//    }
//
//    @RequestMapping(path = "/login", method = RequestMethod.POST)
//    protected void loginPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//
//        String token = securityService.login(login, password);
//
//        if (token != null) {
//            response.addCookie(new Cookie("user-token", token));
//
//            response.sendRedirect("/*");
//        } else {
//            PageGenerator pageGenerator = PageGenerator.getInstance();
//            String page = pageGenerator.getPage("login.html");
//            response.getWriter().write(page);
//            response.getWriter().write("<h3 style=position:absolute;left:33%;>Please enter correct login and password! </h3>");
//        }
//    }
//}
