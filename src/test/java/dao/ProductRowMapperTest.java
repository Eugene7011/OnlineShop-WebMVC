package dao;

import entity.Product;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductRowMapperTest {

    @Test
    public void testProductRowMapper() {
        ProductRowMapper productRowMapper = new ProductRowMapper();
        ResultSet resultSetMock = mock(ResultSet.class);

        try {
            when(resultSetMock.getInt("id")).thenReturn(1);
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get id from database", exception);
        }
        try {
            when(resultSetMock.getString("name")).thenReturn("Lanour");
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get name from database", exception);
        }
        try {
            when(resultSetMock.getInt("price")).thenReturn(6784);
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get price from database", exception);
        }
        try {
            when(resultSetMock.getDate("creationdate")).thenReturn(Date.valueOf("2022-01-01"));
        } catch (SQLException exception) {
            throw new RuntimeException("Can`t get creationdate from database", exception);
        }

        Product actual = null;
        actual = productRowMapper.mapRow(resultSetMock);

        assertEquals(1, actual.getId());
        assertEquals("Lanour", actual.getName());
        assertEquals(6784, actual.getPrice());
        assertEquals("2022-01-01", actual.getCreationDate());
    }
}
