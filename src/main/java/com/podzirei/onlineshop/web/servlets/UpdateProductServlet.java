package com.podzirei.onlineshop.web.servlets;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ServiceLocator;
import com.podzirei.onlineshop.web.util.PageGenerator;
import com.podzirei.onlineshop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {

    private final ProductService productService = (ProductService) ServiceLocator.getBean("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        PageGenerator pageGenerator = PageGenerator.getInstance();
        String page = pageGenerator.getPage("updateproduct.html", pageVariables);

        response.getWriter().write(page);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        LocalDateTime creation_date = LocalDateTime.parse(request.getParameter("creation_date"));

        Product product = new Product(id, name, price, creation_date);
        productService.update(product);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.sendRedirect("/products");
        } catch (IOException exception) {
            throw new RuntimeException("Can not update product");
        }
    }
}
