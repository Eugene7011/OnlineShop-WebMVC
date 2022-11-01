package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import com.podzirei.onlineshop.web.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/products/search")
public class SearchProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public void search(HttpServletRequest request, HttpServletResponse response) {
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
