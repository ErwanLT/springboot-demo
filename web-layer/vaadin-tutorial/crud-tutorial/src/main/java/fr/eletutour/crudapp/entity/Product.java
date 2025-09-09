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
package fr.eletutour.crudapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    private String name;
    
    @NotNull
    @Positive
    private Double price;
    
    private String description;
    
    private Integer stockQuantity;
} 