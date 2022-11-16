package com.podzirei.onlineshop.dao.jdbc;

import com.podzirei.onlineshop.config.DataSourceConfig;
import com.podzirei.onlineshop.config.RootConfig;
import com.podzirei.onlineshop.entity.User;
import com.podzirei.onlineshop.web.config.WebConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class, DataSourceConfig.class})
public class JdbcUserDaoITest {

    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Test
    @DisplayName("find User when User Exist then Data Return")
    public void findUser_whenUserExist_thenDataReturn() {
        User user = jdbcUserDao.findUser("user")
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        int actualId = user.getId();
        int expectedId = 1;

        String actualSalt = user.getSalt();
        String expectedSalt = "4A17982C";

        String actualPassword = user.getPassword();
        String expectedPassword = "a3634ac4a752cfcee2c3e8ff2351347d";

        assertEquals(expectedId, actualId);
        assertEquals(expectedSalt, actualSalt);
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    @DisplayName("find User when User Exist then Return Not Null Data")
    public void findUser_whenUserExist_thenReturnNotNullData() {
        User user = jdbcUserDao.findUser("user")
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        assertNotNull(user);
        assertNotNull(user.getSalt());
        assertNotNull(user.getPassword());
        assertNotNull(user.getLogin());
    }

    @Test
    @DisplayName("find User when User Is Not Exist then throw NullPointerException ")
    public void findUser_whenUserIsNotExist_thenNullPointerExceptionThrow() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            User user = jdbcUserDao.findUser("Not_Existing_User")
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            assertNull(user.getSalt());
            assertNull(user.getPassword());
            assertNull(user.getLogin());
        });
    }
}
