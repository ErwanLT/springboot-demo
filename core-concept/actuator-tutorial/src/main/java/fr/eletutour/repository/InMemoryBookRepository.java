/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of actuator-tutorial.
 *
 * actuator-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * actuator-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with actuator-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.repository;

import fr.eletutour.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    public InMemoryBookRepository() {
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", new Date()));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", new Date()));
        books.add(new Book("The Silmarillion", "J.R.R. Tolkien", new Date()));
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public long count() {
        return books.size();
    }

    @Override
    public long countByBorrowed(boolean borrowed) {
        return books.stream().filter(b -> b.isBorrowed() == borrowed).count();
    }
}
