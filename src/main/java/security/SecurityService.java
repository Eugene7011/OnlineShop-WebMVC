package security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SecurityService {
    private final List<String> TOCKEN_STORAGE = Collections.synchronizedList(new ArrayList<>());

    public boolean isAuth(HttpServletRequest request, List<String> userTokens) {
        boolean isAuth = false;
        Cookie[] cookies = request.getCookies();
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

    public boolean isLoggedIn(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return false;
        }
        return Stream.of(cookies).anyMatch(cookie -> TOCKEN_STORAGE.contains(cookie.getValue()));
    }
}

