/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of i18n-tutorial.
 *
 * i18n-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * i18n-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with i18n-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.Internationalization.dto;

public class UserDto {

    private Long id;
    private String username;
    private String status;
    private String lastLoginDate;

    public UserDto() {
    }

    public UserDto(Long id, String username, String status, String lastLoginDate) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
