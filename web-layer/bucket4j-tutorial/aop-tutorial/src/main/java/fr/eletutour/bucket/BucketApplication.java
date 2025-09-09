/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of aop-tutorial.
 *
 * aop-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aop-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aop-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.bucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BucketApplication {
    public static void main(String[] args) {
        SpringApplication.run(BucketApplication.class, args);
    }
}
