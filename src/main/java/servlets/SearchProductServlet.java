package servlets;

import dao.JdbcProductDao;
import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;
import security.SecurityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {
    private SecurityService securityService = new SecurityService();
    private List<String> userTokens;
    private JdbcProductDao jdbcProductDao = new JdbcProductDao();

    public SearchProductServlet(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = securityService.isAuth(request, userTokens);
        if (isAuth) {
            System.out.println("start search");

            List<Product> products = jdbcProductDao.search(request.getParameter("searchText"));
            Map<String, Object> parametersMap = new HashMap<>();
            parametersMap.put("products", products);
            response.setContentType("text/html; charset=utf-8");
            try {
                response.getWriter().println(PageGenerator.instance().getPage("allproducts.html", parametersMap));
            } catch (IOException exception) {
                throw new RuntimeException("Cant search product from database");
            }
        } else {
            try {
                response.sendRedirect("/login");
            } catch (IOException exception) {
                throw new RuntimeException("You have to log in");
            }
        }
    }
}




