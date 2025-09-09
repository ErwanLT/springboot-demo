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

public class UserV3 extends UserV2 {
    private int age;

    public UserV3(String firstName, String lastName, int age) {
        super(firstName, lastName);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
