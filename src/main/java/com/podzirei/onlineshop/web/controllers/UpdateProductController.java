package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping(path = "/products/update")
public class UpdateProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String updateGetPage() {
        return "updateproduct";
    }

    @PostMapping
    public String updatePost(@RequestParam(value = "id") String id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "price") String price,
                             @RequestParam(value = "creation_date") String creationDate) {

        LocalDateTime date = LocalDateTime.parse(creationDate);

        Product product = new Product(Integer.parseInt(id), name,
                Double.parseDouble(price), date);
        productService.update(product);
        try {
            return "redirect:/products";
        } catch (Exception exception) {
            throw new RuntimeException("Can not update product");
        }
    }
}
