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

    public void update(Product product) {
        productDao.update(product);
    }

    public List<Product> search(String searchText) {
        return productDao.search(searchText);
    }

    public void addToCart(int id) {
        productDao.addToCart(id);
    }

    public List<Product> showCart() {
        return productDao.showCart();
    }

    public void deleteFromCart(int id) {
        productDao.deleteFromCart(id);
    }
}
