package fr.eletutour.ia.library.repository;

import fr.eletutour.ia.library.entity.Book;
import fr.eletutour.ia.library.entity.BookType;
import fr.eletutour.ia.library.entity.LiteraryGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);

    List<Book> findByGenre(LiteraryGenre genre);

    List<Book> findByType(BookType type);
}
