package fr.eletutour.service;

import fr.eletutour.model.Book;
import fr.eletutour.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public long getTotalBooks() {
        return bookRepository.count();
    }

    public long getBorrowedBooks() {
        return bookRepository.countByBorrowed(true);
    }
}
