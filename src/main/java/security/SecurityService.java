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

