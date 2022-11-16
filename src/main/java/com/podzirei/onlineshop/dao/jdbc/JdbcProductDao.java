package com.podzirei.onlineshop.dao.jdbc;

import com.podzirei.onlineshop.dao.ProductDao;
import com.podzirei.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.podzirei.onlineshop.entity.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {
    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String ADD_SQL = "INSERT INTO products (name, price, creation_date) VALUES (?,?,?);";
    private static final String DELETE_SQL = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_SQL = "UPDATE products SET name=?, price=?, creation_date=? WHERE id=?;";
    private static final String SEARCH_SQL = "SELECT id, name, price, creation_date FROM products WHERE  (name) LIKE ?;";
    private static final String FIND_BY_ID_SQL = "SELECT id, name, price, creation_date FROM products WHERE id=?;";
    private static final String FIND_ALL_CART_SQL = "SELECT id, name, price FROM cart;";
    private static final String ADD_TO_CART_SQL = "INSERT INTO cart (id, name, price) VALUES (?,?,?);";
    private static final String DELETE_FROM_CART_SQL = "DELETE FROM cart WHERE id=?;";

    private final static ProductRowMapper productRowMapper = new ProductRowMapper();
    private final DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get products from database", exception);
        }
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SQL)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Can not add product to database", exception);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete product from database", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setLong(4, product.getId());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not edit product from database", e);
        }
    }

    @Override
    public List<Product> search(String searchText) {
        String searchWord = "%" + searchText + "%";
        List<Product> products = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SQL)) {

            preparedStatement.setString(1, searchWord);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product product = productRowMapper.mapRow(resultSet);
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Can not search product with text: " + searchText, e);
        }
    }

    @Override
    public void addToCart(int id) {
        Product product = findProductById(id);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_CART_SQL)) {

            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can add product to cart: " + product.getId(), e);
        }
    }

    @Override
    public List<Product> showCart() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_CART_SQL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRowFromCart(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get products from cart", exception);
        }
    }

    @Override
    public void deleteFromCart(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_CART_SQL)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete product from cart", e);
        }
    }

    private Product findProductById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRowFromCart(resultSet);
                products.add(product);
            }
            return products.get(0);
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get product from database", exception);
        }
    }
}