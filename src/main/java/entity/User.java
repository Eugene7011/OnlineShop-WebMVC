package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String salt;
}
