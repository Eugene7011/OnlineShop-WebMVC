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

public class AllProductsServlet extends HttpServlet {
    private JdbcProductDao jdbcProductDao = new JdbcProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> paramMap = new HashMap<>();

        List<Product> products;
        try {
            products = jdbcProductDao.findAll();

            paramMap.put("products", products);

            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("allproducts.html", paramMap);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(page);
        } catch (IOException exception) {
            throw new RuntimeException("Cant show all products from database");
        }
    }
}
