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
package fr.eletutour.elastic.controller;

import fr.eletutour.elastic.model.Book;
import fr.eletutour.elastic.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }

    @DeleteMapping("/{id}")
    public void deleteBooks(@PathVariable String id) {
        bookService.deleteBook(id);
    }
}