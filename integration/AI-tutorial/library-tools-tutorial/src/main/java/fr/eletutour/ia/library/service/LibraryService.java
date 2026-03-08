package fr.eletutour.ia.library.service;

import fr.eletutour.ia.library.dto.BookView;
import fr.eletutour.ia.library.entity.Author;
import fr.eletutour.ia.library.entity.Book;
import fr.eletutour.ia.library.entity.BookType;
import fr.eletutour.ia.library.entity.LiteraryGenre;
import fr.eletutour.ia.library.repository.AuthorRepository;
import fr.eletutour.ia.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<String> listAuthors() {
        return authorRepository.findAll().stream()
                .map(Author::getName)
                .toList();
    }

    public List<BookView> listBooks() {
        return toBookViews(bookRepository.findAll());
    }

    public List<BookView> findBooksByAuthor(String authorName) {
        return toBookViews(bookRepository.findByAuthorNameContainingIgnoreCase(authorName));
    }

    public List<BookView> findBooksByTitleKeyword(String keyword) {
        return toBookViews(bookRepository.findByTitleContainingIgnoreCase(keyword));
    }

    public List<BookView> findBooksByGenre(LiteraryGenre genre) {
        return toBookViews(bookRepository.findByGenre(genre));
    }

    public List<BookView> findBooksByType(BookType type) {
        return toBookViews(bookRepository.findByType(type));
    }

    public long countBooks() {
        return bookRepository.count();
    }

    private List<BookView> toBookViews(List<Book> books) {
        return books.stream()
                .map(book -> new BookView(
                        book.getTitle(),
                        book.getAuthor() != null ? book.getAuthor().getName() : "Inconnu",
                        book.getIsbn(),
                        book.getGenre() != null ? book.getGenre().name() : null,
                        book.getType() != null ? book.getType().name() : null))
                .toList();
    }
}
