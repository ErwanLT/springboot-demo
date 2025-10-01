package fr.eletutour.service;

import fr.eletutour.exception.BookException;
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

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) throws BookException {
        return bookRepository.findById(id).orElseThrow(() -> new BookException("Book not found"));
    }

    public void deleteBook(Long id) throws BookException {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return null;
    }
}
