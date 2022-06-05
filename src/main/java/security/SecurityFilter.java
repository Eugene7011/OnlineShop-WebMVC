package security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityFilter implements Filter {
    private SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getRequestURI().equals("/login")) {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException("Cant enter to page for add users", e);
            }
            return;
        }
        if (securityService.isLoggedIn(httpServletRequest.getCookies())) {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException ex) {
                throw new RuntimeException("Cant enter to page for add users", ex);
            }
        } else {
            try {
                httpServletResponse.sendRedirect("/login");
            } catch (IOException exp) {
                throw new RuntimeException("Cant enter to page for add users", exp);
            }
        }
    }
}


