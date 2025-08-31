package fr.eletutour.elastic.service;

import fr.eletutour.elastic.model.Book;
import fr.eletutour.elastic.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        logger.info("Saving document: {}", book);
        return bookRepository.save(book);
    }

    public List<Book> searchBooks(String query) {
        logger.info("Searching documents with query: {}", query);
        return bookRepository.findByTitleContainingOrContentContaining(query, query);
    }

    public void deleteBook(String id) {
        logger.info("Deleting document with id: {}", id);
        bookRepository.deleteById(id);
    }
}