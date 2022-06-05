package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;

import java.io.IOException;

public class AllProductsOfShopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("allproducts_shop.html");
        try {
            response.getWriter().println(page);

        } catch (IOException exception) {
            throw new RuntimeException("Cant get data from request");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
