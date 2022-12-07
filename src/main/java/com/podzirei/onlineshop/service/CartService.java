package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final Map<Principal, List<Product>> mapUserToCart = new ConcurrentHashMap<>();

    public void addToCart(Principal principal, int id) {
        List<Product> cart = mapUserToCart.get(principal);
        Product product = productService.findById(id);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(product);
        mapUserToCart.put(principal, cart);
    }

    public Optional<List<Product>> getCart(Principal principal) {
        List<Product> cart = mapUserToCart.get(principal);
        if (cart == null){
            return Optional.empty();
        }
        return Optional.of(cart);
    }

    public void deleteFromCart(Principal principal, int id) {
        List<Product> cart = mapUserToCart.get(principal);
        Product product = productService.findById(id);
        cart.remove(product);
    }
}
