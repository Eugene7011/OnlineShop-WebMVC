package com.podzirei.onlineshop.service;

import com.podzirei.onlineshop.dao.ProductDao;
import com.podzirei.onlineshop.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public void add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        productDao.add(product);
        System.out.println("Product added");
    }

    public void delete(int id) {
        productDao.delete(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }
}
