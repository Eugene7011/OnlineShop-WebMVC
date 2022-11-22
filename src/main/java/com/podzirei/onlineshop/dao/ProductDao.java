package com.podzirei.onlineshop.dao;

import com.podzirei.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    int add(Product product);

    int delete(int id);

    int update(String name, Double price, int id);

    List<Product> search(String searchText);

    Product findById(int id);
}
