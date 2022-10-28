package com.podzirei.onlineshop.web.security;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.podzirei.onlineshop.service.ServiceLocator;
import lombok.extern.slf4j.Slf4j;
import com.podzirei.onlineshop.service.UserService;

import java.io.IOException;
import java.util.List;

@Slf4j
public class SecurityFilter implements Filter {
    private final UserService userService = (UserService) ServiceLocator.getBean("userService");
    private final List<String> allowedPath = List.of("/login");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();
        for (String allowedPath : allowedPath) {
            if (requestURI.startsWith(allowedPath)) {
                chain.doFilter(request, response);
                return;
            }
        }
        log.info("Check if user is authorized");
        if (userService.isAuth(httpServletRequest.getCookies())) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}


