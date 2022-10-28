package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.dao.jdbc.JdbcProductDao;
import com.podzirei.onlineshop.dao.jdbc.JdbcUserDao;
import com.podzirei.onlineshop.security.SecurityService;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<String, Object> REGISTRATION = new HashMap<>();

    static{
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        UserService userService = new UserService(jdbcUserDao);
        ProductService productService = new ProductService(jdbcProductDao);
        SecurityService securityService = new SecurityService(userService);

        REGISTRATION.put("productService", productService);
        REGISTRATION.put("userService", userService);
        REGISTRATION.put("securityService", securityService);
    }

    public static Object getBean(String beanName){
        return REGISTRATION.get(beanName);
    }
}
