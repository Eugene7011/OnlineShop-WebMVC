package servlets;

import dao.jdbc.JdbcProductDao;
import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {
    private JdbcProductDao jdbcProductDao = new JdbcProductDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

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
    }
}




