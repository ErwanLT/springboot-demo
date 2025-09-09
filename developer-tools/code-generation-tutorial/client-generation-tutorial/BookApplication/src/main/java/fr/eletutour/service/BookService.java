/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of BookApplication.
 *
 * BookApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BookApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BookApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.service;

import fr.eletutour.dao.repository.BookRepository;
import fr.eletutour.exception.BookException;
import fr.eletutour.mapper.BookMapper;
import fr.eletutour.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookMapper.toBooks(bookRepository.findAll());
    }

    public Book getBookById(Long id) throws BookException {
        return bookMapper.toBook(bookRepository.findById(id).orElseThrow( () -> new BookException(BookException.BookError.BOOK_NOT_FOUND, "Livre non trouvé pour l'id : " + id)));
    }

    public Book createBook(Book book) {
        var createdBook = bookRepository.save(bookMapper.toBookEntity(book));
        return bookMapper.toBook(createdBook);
    }

    public Book updateBook(Book book) {
        return null;
    }

    public void deleteBook(Long id) throws BookException {
        logger.info("Suppression de l'auteur avec l'ID {}", id);
        if (!bookRepository.existsById(id)) {
            logger.warn("Tentative de suppression d'un auteur inexistant avec l'ID {}", id);
            throw new BookException(BookException.BookError.BOOK_NOT_FOUND, "livre non trouvé pour l'ID : " + id);
        }
        bookRepository.deleteById(id);
        logger.info("Auteur avec l'ID {} supprimé avec succès", id);
    }

    public List<Book> getBooksByAuthor(Long id) {
        var books = bookRepository.findByAuthorId(id);
        return bookMapper.toBooks(books);
    }
}