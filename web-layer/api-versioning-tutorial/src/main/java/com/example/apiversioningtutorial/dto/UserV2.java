/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of api-versioning-tutorial.
 *
 * api-versioning-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * api-versioning-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with api-versioning-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.apiversioningtutorial.dto;

public class UserV2 {
    private String firstName;
    private String lastName;

    public UserV2(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
