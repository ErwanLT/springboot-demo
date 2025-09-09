/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of CRUD Tutorial with Vaadin.
 *
 * CRUD Tutorial with Vaadin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CRUD Tutorial with Vaadin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CRUD Tutorial with Vaadin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.crudapp.repository;

import fr.eletutour.crudapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
} 