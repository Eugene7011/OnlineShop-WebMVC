package com.podzirei.onlineshop.dao.jdbc;

import com.podzirei.onlineshop.config.DataSourceConfig;
import com.podzirei.onlineshop.config.RootConfig;
import com.podzirei.onlineshop.entity.Product;
import com.podzirei.onlineshop.web.config.WebConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class, DataSourceConfig.class})
public class JdbcProductDaoITest {

    @Autowired
    private JdbcProductDao jdbcProductDao;

    @Test
    @DisplayName("when FindAll then Return Not Null Data")
    public void whenFindAll_thenReturnNotNullData() {
        List<Product> products = jdbcProductDao.findAll();
        assertFalse(products.isEmpty());
        for (Product product : products) {
            assertNotNull(product.getName());
            assertNotNull(product.getCreationDate());
        }
    }

    @Test
    @DisplayName("when Add then Size Of List Increase By One")
    void whenAdd_thenSizeOfListIncreaseByOne() {
        Product newProduct = Product.builder().
                name("snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        int sizeBeforeAdd = jdbcProductDao.findAll().size();
        jdbcProductDao.add(newProduct);

        int sizeAfterAdd = jdbcProductDao.findAll().size();
        assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
    }

    @Test
    @DisplayName("when Search New Product By Name then List With Searched Product is Not Empty")
    void whenSearchNewProductByName_thenListWithSearchedProduct_isNotEmpty() {
        Product newProduct = Product.builder().
                name("new_snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        jdbcProductDao.add(newProduct);

        List<Product> listWithSearchedProduct = jdbcProductDao.search("snowboard");
        assertNotNull(listWithSearchedProduct);
    }

    @Test
    @DisplayName("when Update then Size Of List does Not Change")
    void whenUpdate_thenSizeOfList_doesNotChange() {
        Product newProduct = Product.builder().
                name("snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        jdbcProductDao.add(newProduct);

        int sizeBeforeUpdate = jdbcProductDao.findAll().size();
        jdbcProductDao.update(newProduct);
        int sizeAfterUpdate = jdbcProductDao.findAll().size();

        assertEquals(sizeBeforeUpdate, sizeAfterUpdate);
    }
}