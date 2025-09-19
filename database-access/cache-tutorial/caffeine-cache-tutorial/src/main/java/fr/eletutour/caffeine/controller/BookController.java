package fr.eletutour.caffeine.controller;

import fr.eletutour.caffeine.model.Book;
import fr.eletutour.caffeine.service.BookService;
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
