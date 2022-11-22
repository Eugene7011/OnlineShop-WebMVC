package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.dao.ProductDao;
import com.podzirei.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public void add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        productDao.add(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(String name, Double price, int id) {
        productDao.update(name, price, id);
    }

    public Product findById(int id) {
        return productDao.findById(id);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }

}
