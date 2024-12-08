package fr.eletutour.model;

import fr.eletutour.dao.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginResponse {
    private String token;

    private long expiresIn;

    private UserDetails user;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UserDetails getUser() {
        return user;
    }

    public LoginResponse setUser(UserDetails user) {
        this.user = user;
        return this;
    }
}