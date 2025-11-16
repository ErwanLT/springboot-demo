package fr.eletutour.jooq.service;

import fr.eletutour.jooq.dto.BookDto;
import fr.eletutour.jooq.model.tables.pojos.Book;
import fr.eletutour.jooq.repository.BookRepository;
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

    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }

    public void createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.title());
        book.setAuthor(bookDto.author());
        book.setPublicationYear(bookDto.year());
        book.setPrice(bookDto.price());
        bookRepository.save(book);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}
