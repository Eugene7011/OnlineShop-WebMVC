package com.podzirei.onlineshop.web.controllers;

import com.podzirei.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public String deleteById(@RequestParam("id") String id,
                             HttpServletResponse response) throws IOException {
        try {
            productService.delete(Integer.parseInt(id));
            return "redirect:/products";
        } catch (Exception e) {
            response.getWriter().write("<h3 style=position:absolute;left:33%;>Invalid id. Can not delete product by entered id! </h3>");
            return "deleteproduct";
        }
    }
}
