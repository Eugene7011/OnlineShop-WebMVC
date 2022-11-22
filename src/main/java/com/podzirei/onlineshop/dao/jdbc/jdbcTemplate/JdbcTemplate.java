package com.podzirei.onlineshop.dao.jdbc.jdbcTemplate;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcTemplate {

    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        try (ResultSet resultSet = Executor.getConnectionAndExecute(sql, dataSource)) {
            return parseResultset(resultSet, rowMapper);
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get items from database", exception);
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object[] args) {
        try (PreparedStatement preparedStatement = Executor.getConnectionAndPrepare(sql, dataSource)) {
            PreparedStatementSetter.setPreparedStatement(preparedStatement, args);
            ResultSet resultSet = Executor.execute(preparedStatement);
            return parseResultset(resultSet, rowMapper);
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get items from database", exception);
        }
    }

    public int executeUpdate(String sql, Object[] args) {
        try (PreparedStatement preparedStatement = Executor.getConnectionAndPrepare(sql, dataSource)) {
            PreparedStatementSetter.setPreparedStatement(preparedStatement, args);
            return Executor.executeUpdate(preparedStatement);
        } catch (SQLException exception) {
            throw new RuntimeException("Can't perform operation with item to database", exception);
        }
    }

    public <T> T queryObject(String sql, RowMapper<T> rowMapper, Object[] args) {
        try (PreparedStatement preparedStatement = Executor.getConnectionAndPrepare(sql, dataSource)) {
            PreparedStatementSetter.setPreparedStatement(preparedStatement, args);
            ResultSet resultSet = Executor.execute(preparedStatement);
            return parseResultset(resultSet, rowMapper).stream()
                    .findFirst().orElseThrow();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get item from database", exception);
        }
    }

    private <T> List<T> parseResultset(ResultSet resultSet, RowMapper<T> rowMapper) throws SQLException {
        List<T> objects = new ArrayList<>();
        while (resultSet.next()) {
            T object = rowMapper.mapRow(resultSet);
            objects.add(object);
        }
        return objects;
    }
}
