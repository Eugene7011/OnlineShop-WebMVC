package security;

import jakarta.servlet.http.Cookie;

import java.util.List;
import java.util.UUID;

public class SecurityService {
    private List<String> userTokens;

    public SecurityService(List<String> userTokens) {
        this.userTokens = userTokens;
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
}


//    private final List<String> TOKEN_STORAGE = Collections.synchronizedList(new ArrayList<>());
//
//    public boolean isAuth(HttpServletRequest request, List<String> userTokens) {
//        boolean isAuth = false;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("user-token")) {
//                    if (userTokens.contains(cookie.getValue())) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean isLoggedIn(Cookie[] cookies) {
//        if (Objects.isNull(cookies)) {
//            return false;
//        }
//        return Stream.of(cookies).anyMatch(cookie -> TOKEN_STORAGE.contains(cookie.getValue()));
//    }
//


//    public static final String PASSWORD_IS_NOT_VALID_MESSAGE = """
//            Check password.It should be:""";
//
//    private static final Faker FAKER = new Faker();
//    private static final String TOKEN_KEY = "user-token";
//    private final List<String> TOKEN_STORAGE = Collections.synchronizedList(new ArrayList<>());
//
//    public boolean isLoggedIn(Cookie[] cookies) {
//        if (Objects.isNull(cookies)) {
//            return false;
//        }
//        return Stream.of(cookies)
//                .anyMatch(cookie -> TOKEN_STORAGE.contains(cookie.getValue()));
//    }
//
//    public Cookie generateCookie() {
//        String tokenValue = getTokenValue();
//        TOKEN_STORAGE.add(tokenValue);
//        return new Cookie(TOKEN_KEY, tokenValue);
//
//    }
//
//    public void addSalt(User user) {
//        String salt = generateSalt();
//        String saltedPassword = user.getPassword() + salt;
//        String hashedSaltPassword = hash(saltedPassword);
//
//        user.setPassword(hashedSaltPassword);
//        user.setSalt(salt);
//    }
//
//    public boolean isPasswordValid(String password, User existingUser) {
//        String enteredPassword = hash(password + existingUser.getSalt());
//        return enteredPassword.equals(existingUser.getPassword());
//    }
//
//    public boolean isPasswordValid(String password) {
//        return !(password == null || password.isBlank());
//    }
//
//    String hash(String value) {
//        return DigestUtils.md5Hex(value);
//    }
//
//    String generateSalt() {
//        return FAKER.random().hex(8);
//    }
//
//    String getTokenValue() {
//        return UUID.randomUUID().toString();
//    }


