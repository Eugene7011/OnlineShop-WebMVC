package com.podzirei.onlineshop.dao.jdbc.mapper;

import com.podzirei.onlineshop.dao.jdbc.jdbcTemplate.RowMapper;
import com.podzirei.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductRowMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        Timestamp creationDateTimesTemp = resultSet.getTimestamp("creation_date");

        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .creationDate(creationDateTimesTemp.toLocalDateTime())
                .build();
    }
}




