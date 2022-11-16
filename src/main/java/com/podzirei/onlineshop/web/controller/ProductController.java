package com.podzirei.onlineshop.web.controller;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(path = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products;
        products = productService.findAll();
        model.addAttribute("products", products);

        return "allproducts";
    }

    @GetMapping(path = "/add")
    public String addProductPage() {
        return "add_product";
    }

    @PostMapping(path = "/add")
    public String postProduct(@RequestParam(value = "name") String name,
                              @RequestParam(value = "price") String price) {
        Product product = Product.builder()
                .name(name)
                .price(Double.parseDouble(price))
                .build();
        productService.add(product);
        return "redirect:/products";
    }

    @GetMapping(path = "/delete")
    public String getDeletePage() {
        return "deleteproduct";
    }

    @PostMapping(path = "/delete")
    public String deleteById(@RequestParam("id") String id) {
        productService.delete(Integer.parseInt(id));
        return "redirect:/products";
    }

    @GetMapping(path = "/search")
    public String search(@RequestParam("searchText") String searchText,
                         Model model) {

        List<Product> products = productService.search(searchText);
        model.addAttribute("products", products);
        return "allproducts";
    }

    @GetMapping(path = "/update")
    public String updateGetPage() {
        return "updateproduct";
    }

    @PostMapping(path = "/update")
    public String updatePost(@RequestParam(value = "id") String id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "price") String price,
                             @RequestParam(value = "creation_date") String creationDate) {

        LocalDateTime date = LocalDateTime.parse(creationDate);
        Product product = new Product(Integer.parseInt(id), name,
                Double.parseDouble(price), date);
        productService.update(product);

        return "redirect:/products";
    }

    @PostMapping(path = "/cart")
    public String cartPost(@RequestParam(value = "id") String id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "price") String price,
                           @RequestParam(value = "creation_date") String creationDate) {

        LocalDateTime date = LocalDateTime.parse(creationDate);
        Product product = new Product(Integer.parseInt(id), name,
                Double.parseDouble(price), date);
        productService.update(product);

        return "redirect:/products";
    }

}
