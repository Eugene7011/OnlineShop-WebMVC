package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final Map<User, List<Product>> mapUserToCart = new ConcurrentHashMap<>();

    public void addToCart(User user, int id) {
        List<Product> cart = mapUserToCart.get(user);
        Product product = productService.findById(id);
        if (cart == null) {
            cart = new ArrayList<>(1);
        }
        cart.add(product);
        mapUserToCart.put(user, cart);
    }

    public Optional<List<Product>> getCart(User user) {
        List<Product> cart = mapUserToCart.get(user);
        if (cart == null){
            return Optional.empty();
        }
        return Optional.of(cart);
    }

    public void deleteFromCart(User user, int id) {
        List<Product> cart = mapUserToCart.get(user);
        Product product = productService.findById(id);
        cart.remove(product);
    }
}
