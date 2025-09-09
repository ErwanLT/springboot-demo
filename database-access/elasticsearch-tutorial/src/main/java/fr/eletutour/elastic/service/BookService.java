/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of elasticsearch-tutorial.
 *
 * elasticsearch-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * elasticsearch-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with elasticsearch-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.elastic.service;

import fr.eletutour.elastic.model.Book;
import fr.eletutour.elastic.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        logger.info("Saving document: {}", book);
        return bookRepository.save(book);
    }

    public List<Book> searchBooks(String query) {
        logger.info("Searching documents with query: {}", query);
        return bookRepository.findByTitleContainingOrContentContaining(query, query);
    }

    public void deleteBook(String id) {
        logger.info("Deleting document with id: {}", id);
        bookRepository.deleteById(id);
    }
}