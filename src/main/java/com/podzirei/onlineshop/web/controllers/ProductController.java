package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import com.podzirei.onlineshop.web.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = {"/products"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public void getAllProducts(HttpServletResponse response) {

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
            throw new RuntimeException("Can not get all products from database", exception);
        }
    }
}
