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

    public int add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        return productDao.add(product);
    }

    public int delete(int id) {
        return productDao.delete(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public int update(String name, Double price, int id) {
        return productDao.update(name, price, id);
    }

    public Product findById(int id) {
        return productDao.findById(id);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }

}
