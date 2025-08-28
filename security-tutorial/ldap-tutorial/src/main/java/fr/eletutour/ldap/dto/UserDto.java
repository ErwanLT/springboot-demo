package fr.eletutour.ldap.dto;

import java.util.List;

public class UserDto {
    private final String fullName;
    private final String uid;
    private final String email;

    public UserDto(String fullName, String uid, String email) {
        this.fullName = fullName;
        this.uid = uid;
        this.email = email;
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
}