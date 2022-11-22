package com.podzirei.onlineshop.security;

import com.podzirei.onlineshop.entity.User;
import com.podzirei.onlineshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Getter
public class SecurityService {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credentials{
        String login;
        String password;
    }
    private final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();
    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public String encryptPasswordWithSalt(Credentials credentials) {
        String salt = getSalt(credentials.login);
        String passwordWithSalt = credentials.password + salt;
        return hash(passwordWithSalt);
    }

    public User getUser(Credentials credentials) {
        User user = userService.findUser(credentials.getLogin());
        if(user != null) {
            String encryptedPassword = encryptPasswordWithSalt(credentials);
            String passwordRelatedToUser = user.getPassword();
            if (Objects.equals(encryptedPassword, passwordRelatedToUser)) {
                return user;
            }
        }
        return user;
    }

    public Optional<Session> login(Credentials credentials) {
        User user = getUser(credentials);
        if(user != null) {
            Session session = new Session();
            session.setToken(generateToken());
            session.setUser(user);
            sessions.add(session);

            return Optional.of(session);
        }
        return Optional.empty();
    }

    private UUID generateToken() {
        return UUID.randomUUID();
    }

    public String hash(String value) {
        return DigestUtils.md5Hex(value);
    }

    public String getSalt(String login) {
        User user = userService.findUser(login);
        if (user == null){
            throw new IllegalArgumentException("User is not found by login: " + login);
        }
        return user.getSalt();
    }

    public Optional<Session> getSession(List<UUID> tokens) {
        List<UUID> sessionTokens = sessions.stream().map(Session::getToken).toList();
        List<UUID> validTokens = new ArrayList<>(sessionTokens);
        validTokens.retainAll(tokens);

        if (validTokens.isEmpty()){
            return Optional.empty();
        }
        Optional<Session> session = sessions.stream()
                .filter(sess -> validTokens.get(0).equals(sess.getToken()))
                .findFirst();
        return session;
    }

    public Credentials setCredentials(String login, String password) {
        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setPassword(password);
        return credentials;
    }

}


