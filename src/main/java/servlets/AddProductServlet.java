package servlets;

import dao.jdbc.JdbcProductDao;
import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddProductServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPageWithEmptyMap("add_product.html");
        try {
            response.getWriter().write(page);
        } catch (IOException exception) {
            throw new RuntimeException("Can not get page with data from request");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCreationDate(LocalDateTime.now());

        response.setContentType("text/html;charset=utf-8");
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        jdbcProductDao.add(product);
        try {
            response.sendRedirect("/products");
        } catch (IOException e) {
            throw new RuntimeException("Data has not been added. Check and enter correct data in the fields", e);
        }
    }
}




