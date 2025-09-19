package fr.eletutour.redis.service;

import fr.eletutour.redis.model.Book;
import fr.eletutour.redis.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books", key = "#isbn")
    public Book findBookByIsbn(String isbn) {
        long start = System.currentTimeMillis();

        simulateSlowService(); // simule un traitement lourd
        Book book = bookRepository.findByIsbn(isbn);

        long end = System.currentTimeMillis();
        LOG.info("findBookByIsbn({}) exécuté en {} ms", isbn, (end - start));
        LOG.info("Book : {}", book);
        return book;
    }

    @CachePut(value = "books", key = "#book.isbn")
    public Book saveOrUpdateBook(Book book) {
        LOG.info("Mise à jour / ajout du livre [{}] en base et dans le cache", book.getIsbn());
        return bookRepository.save(book);
    }

    @CacheEvict(value = "books", key = "#isbn")
    public void deleteBookByIsbn(String isbn) {
        Optional<Book> book = Optional.ofNullable(bookRepository.findByIsbn(isbn));
        book.ifPresent(b -> {
            bookRepository.delete(b);
            LOG.info("Suppression du livre [{}] en base et invalidation du cache", isbn);
        });
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L); // pause artificielle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
