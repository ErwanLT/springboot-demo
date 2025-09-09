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
package fr.eletutour.cache.controller;

import fr.eletutour.cache.model.Book;
import fr.eletutour.cache.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{isbn}")
    public Book getBook(@PathVariable String isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    @PostMapping
    public Book updateBook(@RequestBody Book book) {
        return bookService.saveOrUpdateBook(book);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        bookService.deleteBookByIsbn(isbn);
    }
}
