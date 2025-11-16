package fr.eletutour.jooq.controller;

import fr.eletutour.jooq.dto.BookDto;
import fr.eletutour.jooq.model.tables.pojos.Book;
import fr.eletutour.jooq.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody BookDto bookDto) {
        bookService.createBook(bookDto);
    }

    @GetMapping("/search")
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return bookService.findBooksByAuthor(author);
    }
}
