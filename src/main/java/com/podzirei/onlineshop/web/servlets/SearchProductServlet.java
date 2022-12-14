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

public class SearchProductServlet extends HttpServlet {

    private final ProductService productService = (ProductService) ServiceLocator.getBean("productService");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productService.search(request.getParameter("searchText"));
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", products);
        try {
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().println(PageGenerator.getInstance().getPage("allproducts.html", parametersMap));
        } catch (IOException exception) {
            throw new RuntimeException("Cant find product from database");
        }
    }
}




