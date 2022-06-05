package dao;

import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final String INSERT_NEW_PRODUCT = "INSERT INTO products VALUES (?,?,?,?)";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id=?";
    private static final String SELECT_PRODUCT = "SELECT id, name, price, creationdate FROM products";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name=?, price=?, creationdate=? WHERE id=?";
    private static final String SEARCH_PRODUCT = "SELECT id, name, price, creationdate FROM products WHERE  (name) LIKE ?;";

    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private ProductRowMapper productRowMapper = new ProductRowMapper();

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = connectionFactory.connectionToDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_PRODUCT)) {
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Can not show all products from database", exception);
        }
        return products;
    }

    @Override
    public void save(Product product) {
        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_PRODUCT)) {

            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDate(4, Date.valueOf(String.valueOf(product.getCreationDate())));
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new RuntimeException("Can not insert a new product into database");
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cant delete product from a database");
        }
    }

    @Override
    public void edit(Product product) {
        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {

            preparedStatement.setLong(4, product.getId());
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cant edit a product from database");
        }
    }

    @Override
    public List<Product> search(String searchText) {
        String searchWord = "%" + searchText + "%";
        List<Product> products = new ArrayList<>();

        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCT)) {

            preparedStatement.setString(1, searchWord);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product product = productRowMapper.mapRow(resultSet);
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Error.Can't search product with text: " + searchText, e);
        }
    }
}
