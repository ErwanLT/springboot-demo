// src/main/java/com/example/library/controller/BookController.java
package fr.eletutour.designpattern.controller;

import fr.eletutour.designpattern.model.Book;
import fr.eletutour.designpattern.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public Book createBook(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String type) {
        return service.createBook(title, author, type);
    }

    @GetMapping
    public List<Book> listBooks() {
        return service.getAllBooks();
    }
}
