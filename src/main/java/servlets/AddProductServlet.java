package servlets;

import dao.jdbc.JdbcProductDao;
import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;
import security.SecurityService;
import service.ProductService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    private JdbcProductDao jdbcProductDao = new JdbcProductDao();
    private SecurityService securityService = new SecurityService();
    private ProductService productService = new ProductService();


    public AddProductServlet() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addproduct.html", pageVariables);
        try {
            response.getWriter().println(page);
        } catch (IOException exception) {
            throw new RuntimeException("Cant get data from request");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        LocalDateTime creation_date = LocalDateTime.parse(request.getParameter("creation_date"));
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCreationDate(creation_date);
        response.setContentType("text/html;charset=utf-8");
        try {
            jdbcProductDao.add(product);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Check new product`s parameters. Cant add to database");
        }
        try {
            response.sendRedirect("/products");
        } catch (IOException e) {
            throw new RuntimeException("Cant show table with update products");
        }
        try {
            response.getWriter().close();
        } catch (IOException exception) {
            throw new RuntimeException("Cant show update products");
        }
    }

    public static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("name", request.getParameter("name"));
        pageVariables.put("price", request.getParameter("price"));
        pageVariables.put("creation_date", request.getParameter("creation_date"));
        return pageVariables;
    }
}



