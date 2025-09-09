/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of prometheus-tutorial.
 *
 * prometheus-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * prometheus-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with prometheus-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name){
        return "Hello " + name;
    }
}
