package dao;

import entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) {
        Product product = new Product();
        try {
            product.setId(resultSet.getInt("id"));
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get id from database", exception);
        }
        try {
            product.setName(resultSet.getString("name"));
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get id from database", exception);
        }
        try {
            product.setPrice(resultSet.getInt("price"));
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get id from database", exception);
        }
        try {
            product.setCreationDate(String.valueOf(resultSet.getDate("creationdate")));
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get id from database", exception);
        }
        return product;
    }
}
