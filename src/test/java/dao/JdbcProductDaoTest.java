package dao;

import entity.Product;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class JdbcProductDaoTest {

    JdbcProductDao jdbcProductDao = new JdbcProductDao();

    @Test
    public void testJdbcProductDaoTest() {
        List<Product> products = jdbcProductDao.getAllProducts();

        assertFalse(products.isEmpty());

        for (Product product : products) {
            assertNotNull(product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getPrice());
            assertNotNull(product.getCreationDate());
        }
    }
}
