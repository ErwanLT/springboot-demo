package fr.eletutour.ldap.dto;

import java.util.List;

public class UserDto {
    private final String fullName;
    private final String uid;
    private final String email;
    private final List<String> roles;


    public UserDto(String fullName, String uid, String email, List<String> roles) {
        this.fullName = fullName;
        this.uid = uid;
        this.email = email;
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
