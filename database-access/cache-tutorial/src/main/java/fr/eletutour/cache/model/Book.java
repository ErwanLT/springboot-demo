/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of cache-tutorial.
 *
 * cache-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * cache-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with cache-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.cache.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; // identifiant technique en base

    private String isbn;
    private String title;

    public Book() {
        // Constructeur par défaut pour JPA
    }

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", isbn='" + isbn + '\'' +
               ", title='" + title + '\'' +
               '}';
    }
}
