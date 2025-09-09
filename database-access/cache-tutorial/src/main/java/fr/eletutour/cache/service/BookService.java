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
package fr.eletutour.cache.service;

import fr.eletutour.cache.model.Book;
import fr.eletutour.cache.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books", key = "#isbn")
    public Book findBookByIsbn(String isbn) {
        long start = System.currentTimeMillis();

        simulateSlowService(); // simule un traitement lourd
        Book book = bookRepository.findByIsbn(isbn);

        long end = System.currentTimeMillis();
        LOG.info("findBookByIsbn({}) exécuté en {} ms", isbn, (end - start));
        LOG.info("Book : {}", book);
        return book;
    }

    @CachePut(value = "books", key = "#book.isbn")
    public Book saveOrUpdateBook(Book book) {
        LOG.info("Mise à jour / ajout du livre [{}] en base et dans le cache", book.getIsbn());
        return bookRepository.save(book);
    }

    @CacheEvict(value = "books", key = "#isbn")
    public void deleteBookByIsbn(String isbn) {
        Optional<Book> book = Optional.ofNullable(bookRepository.findByIsbn(isbn));
        book.ifPresent(b -> {
            bookRepository.delete(b);
            LOG.info("Suppression du livre [{}] en base et invalidation du cache", isbn);
        });
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L); // pause artificielle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
