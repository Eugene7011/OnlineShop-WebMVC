package com.podzirei.onlineshop.dao.repository;

import com.podzirei.onlineshop.dao.ProductDao;
import com.podzirei.onlineshop.dao.repository.jdbcTemplate.JdbcTemplate;
import com.podzirei.onlineshop.dao.repository.mapper.ProductRowMapper;
import com.podzirei.onlineshop.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {
    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String ADD_SQL = "INSERT INTO products (name, price, creation_date) VALUES (?,?,?);";
    private static final String DELETE_SQL = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_SQL = "UPDATE products SET name=?, price=? WHERE id=?;";
    private static final String SEARCH_SQL = "SELECT id, name, price, creation_date FROM products WHERE  name=?;";
    private static final String FIND_BY_ID_SQL = "SELECT id, name, price, creation_date FROM products WHERE id=?;";

    private final static ProductRowMapper productRowMapper = new ProductRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, productRowMapper);
    }

    @Override
    public int add(Product product) {
        Object[] args = {product.getName(), product.getPrice(), product.getCreationDate()};
        return jdbcTemplate.executeUpdate(ADD_SQL, args);
    }

    @Override
    public int delete(int id) {
        Object[] args = {id};
        return jdbcTemplate.executeUpdate(DELETE_SQL, args);
    }

    @Override
    public int update(String name, Double price, int id) {
        Object[] args = {name, price, id};
        return jdbcTemplate.executeUpdate(UPDATE_SQL, args);
    }

    @Override
    public List<Product> search(String searchText) {
        Object[] args = {searchText};
        return jdbcTemplate.query(SEARCH_SQL, productRowMapper, args);
    }

    public Product findById(int id) {
        Object[] args = {id};
        return jdbcTemplate.queryObject(FIND_BY_ID_SQL, productRowMapper, args);
    }
}