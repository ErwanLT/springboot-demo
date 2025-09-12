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