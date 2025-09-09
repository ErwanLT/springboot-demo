/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of aspect-tutorial.
 *
 * aspect-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aspect-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aspect-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.tutorial.aop.service;

import org.springframework.stereotype.Service;

@Service
public class TutorialService {

    public String sayHello(String name) {
        return "Hello " + name;
    }
}
