/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of thymeleaf-tutorial.
 *
 * thymeleaf-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * thymeleaf-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with thymeleaf-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.model.Author;
import fr.eletutour.model.Book;
import fr.eletutour.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/authors";
    }

    @GetMapping("/authors")
    public String showAuthors(Model model, @RequestParam(required = false) String keyword) {
        List<Author> authors;
        if (keyword != null && !keyword.isEmpty()) {
            authors = libraryService.searchAuthors(keyword);
        } else {
            authors = libraryService.getAllAuthors();
        }
        model.addAttribute("authors", authors);
        model.addAttribute("keyword", keyword);
        model.addAttribute("newAuthor", new Author());
        return "authors";
    }

    @GetMapping("/books")
    public String showBooks(Model model, @RequestParam(required = false) String keyword) {
        List<Book> books;
        if (keyword != null && !keyword.isEmpty()) {
            books = libraryService.searchBooks(keyword);
        } else {
            books = libraryService.getAllBooks();
        }
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "books";
    }

    @GetMapping("/author/{id}")
    public String showAuthorDetails(@PathVariable Long id, Model model) {
        libraryService.getAuthorById(id).ifPresent(author -> {
            model.addAttribute("author", author);
            Book newBook = new Book();
            newBook.setAuthor(author);
            model.addAttribute("newBook", newBook);
        });
        return "author-details";
    }

    @PostMapping("/author")
    public String addAuthor(@Valid @ModelAttribute("newAuthor") Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", libraryService.getAllAuthors());
            model.addAttribute("keyword", null);
            return "authors";
        }
        libraryService.saveAuthor(author);
        return "redirect:/authors";
    }

    @PostMapping("/book")
    public String addBook(@Valid @ModelAttribute("newBook") Book book, BindingResult result, @RequestParam Long authorId, Model model) {
        if (result.hasErrors()) {
            libraryService.getAuthorById(authorId).ifPresent(author -> model.addAttribute("author", author));
            return "author-details";
        }
        libraryService.getAuthorById(authorId).ifPresent(book::setAuthor);
        libraryService.saveBook(book);
        return "redirect:/author/" + authorId;
    }
    
    @GetMapping("/delete-author/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        libraryService.deleteAuthor(id);
        return "redirect:/authors";
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id) {
        Book book = libraryService.getBookById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        libraryService.deleteBook(id);
        return "redirect:/author/" + book.getAuthor().getId();
    }
}
