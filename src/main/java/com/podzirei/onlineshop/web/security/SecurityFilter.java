//package com.podzirei.onlineshop.web.security;
//
//import com.podzirei.onlineshop.security.SecurityService;
//import com.podzirei.onlineshop.security.Session;
//import com.podzirei.onlineshop.service.CurrentUser;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Component("securityFilter")
//public class SecurityFilter implements Filter {
//    private final SecurityService securityService;
//    private final List<String> allowedPath = List.of("/login");
//
//    public SecurityFilter(SecurityService securityService) {
//        this.securityService = securityService;
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String requestURI = request.getRequestURI();
//        for (String allowedPath : allowedPath) {
//            if (requestURI.startsWith(allowedPath)) {
//                chain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//        }
//        Optional<Session> sessionOptional = securityService.getSession(retrieveTokens(request));
//        if (sessionOptional.isEmpty()) {
//            response.sendRedirect("/login");
//        }
//
//        Session session = sessionOptional.get();
//        servletRequest.setAttribute("session", session);
//        CurrentUser.setCurrentUser(session.getUser());
//
//        try {
//            chain.doFilter(servletRequest, servletResponse);
//        } finally {
//            CurrentUser.remove();
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    private List<UUID> retrieveTokens(HttpServletRequest httpServletRequest) {
//
//        List<UUID> tokens = Arrays.stream(httpServletRequest.getCookies())
//                .filter(cookie -> ("userToken").equals(cookie.getName()))
//                .map(Cookie::getValue)
//                .toList()
//                .stream()
//                .map(UUID::fromString)
//                .collect(Collectors.toList());
//        return tokens;
//    }
//}
//
//
