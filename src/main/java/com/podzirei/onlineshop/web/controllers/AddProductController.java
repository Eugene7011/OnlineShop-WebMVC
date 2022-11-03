package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/products/add")
public class AddProductController {

    @Autowired
    private ProductService productService;

    public AddProductController() {
        super();
    }

    @GetMapping
    public String addProductPage() {
        return "add_product";
    }

    @PostMapping
    public String postProduct(@RequestParam(value = "name") String name,
                              @RequestParam(value = "price") String price,
                              Model model) {
        try {
            Product product = Product.builder()
                    .name(name)
                    .price(Double.parseDouble(price))
                    .build();
            productService.add(product);
            return "redirect:/products";

        } catch (Exception e) {
            String errorMessage = "Product has not been added. Check and enter correct data in the fields";
            model.addAttribute("errorMessage", errorMessage);
            return "add_product";
        }
    }
}
