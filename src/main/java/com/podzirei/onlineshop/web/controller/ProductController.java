package com.podzirei.onlineshop.web.controller;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.service.CartService;
import com.podzirei.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "allproducts";
    }

    @GetMapping(path = "/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String addProductPage() {
        return "add_product";
    }

    @PostMapping(path = "/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getDeletePage() {
        return "deleteproduct";
    }

    @PostMapping(path = "/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String deleteById(@RequestParam("id") int id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping(path = "/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String search(@RequestParam("searchText") String searchText,
                         Model model) {

        List<Product> products = productService.search(searchText);
        model.addAttribute("products", products);
        return "allproducts";
    }

    @GetMapping(path = "/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String updateGetPage() {
        return "updateproduct";
    }

    @PostMapping(path = "/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String updatePost(@RequestParam(value = "id") int id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "price") double price) {

        productService.update(name, price, id);
        return "redirect:/products";
    }

    @GetMapping(path = "/cart/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String addToCart() {
        return "addtocart";
    }

    @PostMapping(path = "/cart/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String addToCart(@RequestParam("id") int id, Principal principal) {
        cartService.addToCart(principal, id);
        return "redirect:/products/cart";
    }

    @GetMapping(path = "/cart")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getCart(@RequestParam Principal principal, Model model) {
        Optional<List<Product>> cartOptional = cartService.getCart(principal);

        if (cartOptional.isEmpty()) {
            List<Product> emptyCart = new ArrayList<>();
            model.addAttribute("cart", emptyCart);
            return "cart";
        }
        List<Product> cart = cartOptional.get();
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping(path = "/cart/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String deleteFromCart() {
        return "deletefromcart";
    }

    @PostMapping(path = "/cart/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String deleteFromCart(@RequestParam("id") int id, Principal principal) {
        cartService.deleteFromCart(principal, id);
        return "redirect:/products/cart";
    }
}
