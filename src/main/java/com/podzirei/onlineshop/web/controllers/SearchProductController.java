package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/products/search")
public class SearchProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String search(@RequestParam("searchText") String searchText,
                         Model model) {
        try {
            List<Product> products = productService.search(searchText);
            model.addAttribute("products", products);
            return "allproducts";
        } catch (Exception exception) {
            throw new RuntimeException("Cant find product from database");
        }
    }
}
