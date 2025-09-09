/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of ldap-tutorial.
 *
 * ldap-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ldap-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ldap-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
