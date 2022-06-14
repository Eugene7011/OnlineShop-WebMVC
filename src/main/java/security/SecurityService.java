package security;

import com.github.javafaker.Faker;
import dao.UserDao;
import entity.User;
import jakarta.servlet.http.Cookie;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SecurityService {
    private List<String> userTokens;
    private static final Faker FAKER = new Faker();
    UserDao userDao;

    public SecurityService(List<String> userTokens, UserDao userDao) {
        this.userTokens = userTokens;
        this.userDao = userDao;
    }

    public Cookie generateCookie() {
        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);
        return new Cookie("user-token", userToken);
    }

    public boolean isAuth(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String encryptPasswordWithSalt(String password, String login) {
        String salt = getSalt(login);
        String passwordWithSalt = password + salt;

        return hash(passwordWithSalt);
    }

    public boolean checkPassword(String login, String password) {
        User userFromDB = userDao.findUser(login);
        String encryptedPassword = encryptPasswordWithSalt(password, login);
        String passwordFromDB = userFromDB.getPassword();
        return Objects.equals(encryptedPassword, passwordFromDB);
    }

    public static String hash(String value) {
        return DigestUtils.md5Hex(value);
    } // Visible for testing

    public String generateSalt() {
        return FAKER.random().hex(8);
    }

    public String getSalt(String login) {
        User userLogin = userDao.findUser(login);
        return userLogin.getSalt();
    }
}

