package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/products/delete")
public class DeleteProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getDeletePage() {
        return "deleteproduct";
    }

    @PostMapping
    public String deleteById(@RequestParam("id") String id) {
        try {
            productService.delete(Integer.parseInt(id));
            return "/products";
        } catch (Exception e) {
            throw new RuntimeException("Can not delete product by id", e);
        }
    }
}
