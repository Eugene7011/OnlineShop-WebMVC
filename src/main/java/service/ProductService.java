package service;

import dao.ProductDao;
import entity.Product;

import java.time.LocalDateTime;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void add(Product product){
        LocalDateTime now=LocalDateTime.now();
        product.setCreationDate(now);
        productDao.add(product);
        System.out.println("Product added");
    }
}
