/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of spel-tutorial.
 *
 * spel-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * spel-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with spel-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.spel.model;

import org.springframework.stereotype.Component;

@Component("myCar")
public class Car {
    private String make = "GoodCar";
    private int year = 2021;
    private Engine engine = null; // This will be null

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public boolean isNewerThan(int year) {
        return this.year > year;
    }

    public Engine getEngine() {
        return engine;
    }
}
