package com.podzirei.onlineshop.dao;

import com.podzirei.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    void add(Product product);

    void delete(int id);

    void update(Product product);

    List<Product> search(String searchText);

    void addToCart(int id);

    List<Product> showCart();

    void deleteFromCart(int id);
}
