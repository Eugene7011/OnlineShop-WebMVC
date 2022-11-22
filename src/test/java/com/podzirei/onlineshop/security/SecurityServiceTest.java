package com.podzirei.onlineshop.security;

import com.podzirei.onlineshop.dao.jdbc.config.DataSourceConfig;
import com.podzirei.onlineshop.web.config.RootConfig;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class, DataSourceConfig.class})
public class SecurityServiceTest {

    @Autowired
    private SecurityService securityService;

    @Test
    @DisplayName("test Encrypt Password With Salt")
    public void testEncryptPasswordWithSalt() {
        SecurityService.Credentials credentials = new SecurityService.Credentials("user", "pass");
        String actualEncryptPassword = securityService.encryptPasswordWithSalt(credentials);
        String expectedEncryptPassword = "a3634ac4a752cfcee2c3e8ff2351347d";

        assertEquals(expectedEncryptPassword, actualEncryptPassword);
    }

    @Test
    @DisplayName("test Generate Salt")
    public void testGenerateSalt() {
        String actualSalt = securityService.getSalt("user");
        String expectedSalt = "4A17982C";

        assertNotNull(securityService.getSalt("user"));
        assertEquals(expectedSalt, actualSalt);
    }

    @Test
    @DisplayName("test Generate Hash")
    public void testGenerateHash() {
        String actualHash = securityService.hash("user");
        String expectedHash = "ee11cbb19052e40b07aac0ca060c23ee";

        assertNotNull(actualHash);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    @DisplayName("test Login when User And Password are Not correct")
    public void testLogin_whenUserAndPassword_areNotCorrect() {
        SecurityService.Credentials credentials = new SecurityService.Credentials("user1", "NotExistingPassword");
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            securityService.login(credentials);
        });
    }

    @Test
    @DisplayName("test Login when User And Password are correct")
    public void testLogin_whenUserAndPassword_areCorrect() {
        String expectedValue = "0";
        SecurityService.Credentials credentials = new SecurityService.Credentials("user", "pass");
        Optional<Session> login = securityService.login(credentials);

        assertNotEquals(expectedValue, login.toString());
    }
}
