package servlets;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private List<String> userTokens;

    public LoginServlet(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html", pageVariables);
        try {
            response.getWriter().write(page);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);

        Cookie cookie = new Cookie("user-token", userToken);
        response.addCookie(cookie);
        try {
            response.sendRedirect("/products");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("email", request.getParameter("email"));
        pageVariables.put("password", request.getParameter("password"));

        return pageVariables;
    }
}
