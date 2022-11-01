package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import com.podzirei.onlineshop.web.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(path = "/products/add")
public class AddProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public void addProduct(HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.getInstance();
        String page = pageGenerator.getPage("add_product.html");
        response.getWriter().write(page);
    }

    @PostMapping
    public void postProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Product product = getProductFromRequest(request);
            productService.add(product);
            response.sendRedirect("/products");

        } catch (IOException e) {
            String errorMessage = "Product has not been added. Check and enter correct data in the fields";
            PageGenerator pageGenerator = PageGenerator.getInstance();

            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("add_product.html", parameters);
            response.getWriter().write(page);
        }
    }

    private Product getProductFromRequest(HttpServletRequest request) {
        return Product.builder().
                name(request.getParameter("name"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();
    }
}
