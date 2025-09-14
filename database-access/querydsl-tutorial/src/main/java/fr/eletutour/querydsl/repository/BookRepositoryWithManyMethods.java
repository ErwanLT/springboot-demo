package fr.eletutour.querydsl.repository;

import fr.eletutour.querydsl.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is for demonstration purposes only.
 * It shows the proliferation of methods that would be needed without QueryDSL.
 */
@Repository
public interface BookRepositoryWithManyMethods extends JpaRepository<Book, Long> {

    // Single criteria
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByAuthor(String author, Pageable pageable);
    Page<Book> findByYearAfter(Integer year, Pageable pageable);

    // Two criteria
    Page<Book> findByTitleContainingIgnoreCaseAndAuthor(String title, String author, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCaseAndYearAfter(String title, Integer year, Pageable pageable);
    Page<Book> findByAuthorAndYearAfter(String author, Integer year, Pageable pageable);

    // Three criteria
    Page<Book> findByTitleContainingIgnoreCaseAndAuthorAndYearAfter(String title, String author, Integer year, Pageable pageable);
    
    // And so on for all 2^8 = 256 combinations...
}
