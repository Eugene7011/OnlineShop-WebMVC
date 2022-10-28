package com.podzirei.onlineshop.web.servlets;

import com.podzirei.onlineshop.entity.Product;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.podzirei.onlineshop.service.ServiceLocator;
import com.podzirei.onlineshop.web.util.PageGenerator;
import com.podzirei.onlineshop.service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProductsServlet extends HttpServlet {
    private final ProductService productService = (ProductService) ServiceLocator.getBean("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> paramMap = new HashMap<>();
        List<Product> products;

        try {
            products = productService.findAll();
            paramMap.put("products", products);
            PageGenerator pageGenerator = PageGenerator.getInstance();
            String page = pageGenerator.getPage("allproducts.html", paramMap);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(page);
        } catch (IOException exception) {
            throw new RuntimeException("Can not get all products from database",exception);
        }
    }
}
